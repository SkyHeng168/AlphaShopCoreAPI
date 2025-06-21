package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.dto.InventoryDto.InventoryRequest;
import com.alphatech.alphatech.dto.InventoryDto.InventoryRespond;
import com.alphatech.alphatech.service.impl.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryRespond> createInventory(@RequestBody InventoryRequest inventoryRequest) {
        InventoryRespond inventoryRespond = inventoryService.createInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryRespond);
    }

    @GetMapping
    public ResponseEntity<List<InventoryRespond>> findAll() {
        List<InventoryRespond> inventoryResponds = inventoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponds);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryRespond> updateInventory(@PathVariable Long id, @RequestBody InventoryRequest inventoryRequest) {
        InventoryRespond inventoryRespond = inventoryService.updateInventory(id, inventoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryRespond);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.status(HttpStatus.OK).body("Inventory deleted");
    }
}
