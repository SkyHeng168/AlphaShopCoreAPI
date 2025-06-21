package com.alphatech.alphatech.dto.StockMovementDto;

import com.alphatech.alphatech.enums.MovementType;
import com.alphatech.alphatech.model.StockMovement;

import java.time.format.DateTimeFormatter;

public record StockMovementRespond(
        Long id,
        Long inventory,
        String productName,
        MovementType movementType,
        int quantity,
        String note,
        String stockMovementDate
) {
    public static StockMovementRespond stockMovementRespondConvert(StockMovement stockMovement) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        return new StockMovementRespond(
                stockMovement.getId(),
                stockMovement.getInventory() != null ? stockMovement.getInventory().getId() : null,
                stockMovement.getInventory() != null ? stockMovement.getInventory().getProduct().getProductName() : null,
                stockMovement.getMovementType(),
                stockMovement.getQuantity(),
                stockMovement.getNote(),
                stockMovement.getStockMovementDate() != null ? stockMovement.getStockMovementDate().format(dtf) : null
        );
    }
}
