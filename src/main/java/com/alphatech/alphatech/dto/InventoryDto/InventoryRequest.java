package com.alphatech.alphatech.dto.InventoryDto;

public record InventoryRequest(
        Long product,
        Long warehouse,
        Integer quantity
) {
}
