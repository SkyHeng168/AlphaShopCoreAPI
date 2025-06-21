package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.dto.StockMovementDto.StockMovementRespond;
import com.alphatech.alphatech.service.impl.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @GetMapping("/{id}/movements")
    public ResponseEntity<List<StockMovementRespond>> getStockMovementInventoryId(@PathVariable Long id) {
        List<StockMovementRespond> logs = stockMovementService.getAllStockMovements(id);
        return ResponseEntity.status(HttpStatus.OK).body(logs);
    }
}
