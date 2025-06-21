package com.alphatech.alphatech.dto.categoryDto;

import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.enums.CategoryStatus;
import com.alphatech.alphatech.model.Category;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record CategoryRespond(
        Long id,
        String categoryName,
        String categoryLogo,
        String description,
        CategoryStatus categoryStatus,
        String createDate,
        String updateDate,
        CategorySubDTO categorySubDTO,
        List<ProductRespond> products
) {
    public static CategoryRespond fromEntity(Category category) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");

        return new CategoryRespond(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryLogo(),
                category.getDescription(),
                category.getCategoryStatus(),
                category.getCreatedDate() != null ? category.getCreatedDate().format(dtf) : null,
                category.getUpdatedDate() != null ? category.getUpdatedDate().format(dtf) : null,
                CategorySubDTO.fromEntity(category.getSubCategory()),
                category.getProducts() != null ? category.getProducts().stream()
                        .map(ProductRespond::convertEntityToDto)
                        .collect(Collectors.toList()) : null
        );
    }
}
