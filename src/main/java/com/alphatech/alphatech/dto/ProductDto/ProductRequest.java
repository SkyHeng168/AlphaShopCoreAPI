package com.alphatech.alphatech.dto.ProductDto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductRequest(
        String productName,
        String description,
        BigDecimal basePrice,
        BigDecimal currentPrice,
        String sku,
        MultipartFile imageUrl,
        boolean isFeature,
        String status,
        Long wareHouse,
        Long category,
        Long brand,
        Long supplier,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
