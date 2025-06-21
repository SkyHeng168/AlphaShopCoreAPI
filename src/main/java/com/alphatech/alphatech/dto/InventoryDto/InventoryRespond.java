package com.alphatech.alphatech.dto.InventoryDto;

import com.alphatech.alphatech.model.Inventory;

public record InventoryRespond(
        Long id,
        String product,
        String warehouse,
        Integer quantity
) {
    public static InventoryRespond convertToInventoryRespond(Inventory inventory) {
       return new InventoryRespond(
               inventory.getId(),
               inventory.getProduct() != null ? inventory.getProduct().getProductName() : null,
               inventory.getWarehouse() != null ? inventory.getWarehouse().getWarehouseName() : null,
               inventory.getQuantity()
       );
    }
}
