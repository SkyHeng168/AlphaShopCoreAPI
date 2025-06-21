package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.InventoryDto.InventoryRequest;
import com.alphatech.alphatech.dto.InventoryDto.InventoryRespond;

import java.util.List;

public interface IInventoryService {
    InventoryRespond createInventory(InventoryRequest inventoryRequest);
    List<InventoryRespond> findAll();
    InventoryRespond updateInventory(Long id, InventoryRequest inventoryRequest);
    void deleteInventory(Long id);
}
