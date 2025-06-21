package com.alphatech.alphatech.service;


import com.alphatech.alphatech.dto.StockMovementDto.StockMovementRespond;

import java.util.List;

public interface IStockMovementService {
    List<StockMovementRespond> getAllStockMovements(Long inventoryId);
}
