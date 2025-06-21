package com.alphatech.alphatech.dto.ProductDto;

import com.alphatech.alphatech.enums.ProductStatus;
import com.alphatech.alphatech.model.Product;

import java.math.BigDecimal;

public record ProductRespond(
        Long id,
        String productName,
        String description,
        BigDecimal basePrice,
        BigDecimal currentPrice,
        String sku,
        String imageUrl,
        boolean isFeature,
        ProductStatus status,
        String wareHouse,
        String category,
        String brand,
        String suppliers
) {
    public static ProductRespond convertEntityToDto(Product product) {
        return new ProductRespond(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getBasePrice(),
                product.getCurrentPrice(),
                product.getSku(),
                product.getImageUrl(),
                product.isFeature(),
                product.getStatus(),
                product.getWareHouse() != null ? product.getWareHouse().getWarehouseName() : null,
                product.getCategory() != null ? product.getCategory().getCategoryName() : null,
                product.getBrand() != null ? product.getBrand().getBrandName() : null,
                product.getSuppliers() != null ? product.getSuppliers().getSupplierName() : null
        );
    }
}
