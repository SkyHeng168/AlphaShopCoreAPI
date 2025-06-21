package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceAlreadyExistsException;
import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.Exception.customException.WarehouseCapacityExceededException;
import com.alphatech.alphatech.dto.InventoryDto.InventoryRequest;
import com.alphatech.alphatech.dto.InventoryDto.InventoryRespond;
import com.alphatech.alphatech.enums.MovementType;
import com.alphatech.alphatech.model.Inventory;
import com.alphatech.alphatech.model.Product;
import com.alphatech.alphatech.model.StockMovement;
import com.alphatech.alphatech.model.WareHouse;
import com.alphatech.alphatech.repository.InventoryRepository;
import com.alphatech.alphatech.repository.ProductRepository;
import com.alphatech.alphatech.repository.StockMovementRepository;
import com.alphatech.alphatech.repository.WareHouseRepository;
import com.alphatech.alphatech.service.IInventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class InventoryService implements IInventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WareHouseRepository wareHouseRepository;
    private final StockMovementRepository stockMovementRepository;

    @Override
    public InventoryRespond createInventory(InventoryRequest inventoryRequest) {
        boolean exists = inventoryRepository.existsByProductIdAndWarehouseId(
                inventoryRequest.product(),
                inventoryRequest.warehouse()
        );
        if (exists) {
            throw new ResourceAlreadyExistsException("This product already exists in this warehouse.");
        }

        Product product = productRepository.findById(inventoryRequest.product())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        WareHouse warehouse = wareHouseRepository.findById(inventoryRequest.warehouse())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        int usedCapacity = inventoryRepository.sumQuantityByWareHouse(warehouse.getId());
        int incomingQuantity = inventoryRequest.quantity();

        if (usedCapacity + incomingQuantity > warehouse.getCapacity()) {
            int available = warehouse.getCapacity() - usedCapacity;
            throw new WarehouseCapacityExceededException("Cannot insert: Warehouse only has " + available + " units of free space.");
        }

        Inventory inventory = Inventory.builder()
                .product(product)
                .warehouse(warehouse)
                .quantity(inventoryRequest.quantity())
                .build();

        Inventory saved = inventoryRepository.save(inventory);

        logStockMovement(saved, MovementType.STOCK_IN, inventoryRequest.quantity(), "Initial stock added");
        return InventoryRespond.convertToInventoryRespond(saved);
    }

    @Override
    public List<InventoryRespond> findAll() {
        List<Inventory> inventoryResponds = inventoryRepository.findAll();
        if (inventoryResponds.isEmpty()) {
            log.info("Inventory service not found");
            throw new ResourceNotFoundException("Inventory service not found");
        }
        return inventoryResponds.stream()
                .map(InventoryRespond::convertToInventoryRespond)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryRespond updateInventory(Long id, InventoryRequest inventoryRequest) {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        boolean productChanged = !existingInventory.getProduct().getId().equals(inventoryRequest.product());
        boolean warehouseChanged = !existingInventory.getWarehouse().getId().equals(inventoryRequest.warehouse());

        if ((productChanged || warehouseChanged)
                && inventoryRepository.existsByProductIdAndWarehouseId(inventoryRequest.product(), inventoryRequest.warehouse())) {
            throw new ResourceAlreadyExistsException("This product already exists in this warehouse.");
        }

        Product newProduct = productRepository.findById(inventoryRequest.product())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        WareHouse newWarehouse = wareHouseRepository.findById(inventoryRequest.warehouse())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        int usedCapacity = inventoryRepository.sumQuantityByWareHouse(newWarehouse.getId());
        int currentQuantity = existingInventory.getQuantity(); // old quantity
        int newQuantity = inventoryRequest.quantity();

        // Adjust usedCapacity to exclude current quantity from the same record
        int adjustedUsedCapacity = usedCapacity - currentQuantity;

        if (adjustedUsedCapacity + newQuantity > newWarehouse.getCapacity()) {
            int available = newWarehouse.getCapacity() - adjustedUsedCapacity;
            throw new WarehouseCapacityExceededException("Cannot update: Warehouse only has " + available + " units of free space.");
        }

        MovementType movementType;
        int quantityDiff = newQuantity - currentQuantity;

        existingInventory.setProduct(newProduct);
        existingInventory.setWarehouse(newWarehouse);
        existingInventory.setQuantity(newQuantity);

        Inventory saved = inventoryRepository.save(existingInventory);

        if (quantityDiff != 0) {
            movementType = (quantityDiff > 0) ? MovementType.STOCK_IN : MovementType.STOCK_OUT;
            logStockMovement(saved, movementType, Math.abs(quantityDiff), "Inventory updated");
        }

        return InventoryRespond.convertToInventoryRespond(saved);
    }


    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        inventoryRepository.delete(inventory);
    }

    private void logStockMovement(Inventory inventory, MovementType type, int quantity, String note){
        StockMovement movement = StockMovement.builder()
                .inventory(inventory)
                .movementType(type)
                .quantity(quantity)
                .note(note)
                .stockMovementDate(LocalDateTime.now())
                .build();
        stockMovementRepository.save(movement);
    }
}
