package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceAlreadyExistsException;
import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.WareHouseDto.WareHouseRequest;
import com.alphatech.alphatech.dto.WareHouseDto.WareHouseRespond;
import com.alphatech.alphatech.model.WareHouse;
import com.alphatech.alphatech.repository.WareHouseRepository;
import com.alphatech.alphatech.service.IWareHouseService;
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
public class WareHouseService implements IWareHouseService {
    private final WareHouseRepository wareHouseRepository;

    @Override
    public WareHouseRespond createWareHouse(WareHouseRequest wareHouseRequest) {
        if (wareHouseRepository.existsWareHouseByWarehouseNameIgnoreCase(wareHouseRequest.warehouseName())) {
            throw new ResourceAlreadyExistsException("Warehouse Name Already Exists");
        }
        WareHouse wareHouse = WareHouse.builder()
                .warehouseName(wareHouseRequest.warehouseName())
                .warehouseCode(wareHouseRequest.warehouseCode())
                .location(wareHouseRequest.location())
                .contactPhone(wareHouseRequest.contactPhone())
                .managerName(wareHouseRequest.managerName())
                .managerEmail(wareHouseRequest.managerEmail())
                .capacity(wareHouseRequest.capacity())
                .status(wareHouseRequest.status())
                .note(wareHouseRequest.note())
                .createdDate(LocalDateTime.now())
                .build();
        WareHouse savedWareHouse = wareHouseRepository.save(wareHouse);
        return WareHouseRespond.convertToWareHouseRespond(savedWareHouse);
    }

    @Override
    public List<WareHouseRespond> getAllWareHouse() {
        List<WareHouse> wareHouseResponds = wareHouseRepository.findAll();
        if(wareHouseResponds.isEmpty()){
            throw new ResourceNotFoundException("Warehouse not found.");
        }
        return wareHouseResponds.stream()
                .map(WareHouseRespond::convertToWareHouseRespond)
                .collect(Collectors.toList());
    }

    @Override
    public WareHouseRespond updateWareHouse(Long id, WareHouseRequest wareHouseRequest) {
        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found."));
        boolean exists = wareHouseRepository.existsWareHouseByWarehouseNameAndWarehouseCodeIgnoreCase(wareHouseRequest.warehouseName(), wareHouseRequest.warehouseCode());
        if(exists && !wareHouse.getWarehouseCode().equalsIgnoreCase(wareHouseRequest.warehouseCode())) {
            throw new ResourceAlreadyExistsException("Warehouse Code Already Exists");
        }
        wareHouse.setWarehouseName(wareHouseRequest.warehouseName());
        wareHouse.setWarehouseCode(wareHouseRequest.warehouseCode());
        wareHouse.setLocation(wareHouseRequest.location());
        wareHouse.setContactPhone(wareHouseRequest.contactPhone());
        wareHouse.setManagerName(wareHouseRequest.managerName());
        wareHouse.setManagerEmail(wareHouseRequest.managerEmail());
        wareHouse.setManagerEmail(wareHouseRequest.managerEmail());
        wareHouse.setCapacity(wareHouseRequest.capacity());
        wareHouse.setStatus(wareHouseRequest.status());
        wareHouse.setNote(wareHouseRequest.note());
        wareHouse.setUpdatedDate(LocalDateTime.now());
        WareHouse savedWareHouse = wareHouseRepository.save(wareHouse);
        return WareHouseRespond.convertToWareHouseRespond(savedWareHouse);
    }

    @Override
    public void deleteWareHouse(Long id) {
        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found."));
        wareHouseRepository.delete(wareHouse);
    }
}
