package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.dto.StockMovementDto.StockMovementRespond;
import com.alphatech.alphatech.model.StockMovement;
import com.alphatech.alphatech.repository.StockMovementRepository;
import com.alphatech.alphatech.service.IStockMovementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StockMovementService implements IStockMovementService {
    private final StockMovementRepository stockMovementRepository;

    @Override
    public List<StockMovementRespond> getAllStockMovements(Long inventoryId) {
        List<StockMovement> stockMovements = stockMovementRepository.findByInventoryId(inventoryId);
        return stockMovements.stream()
                .map(StockMovementRespond::stockMovementRespondConvert)
                .collect(Collectors.toList());
    }
}
