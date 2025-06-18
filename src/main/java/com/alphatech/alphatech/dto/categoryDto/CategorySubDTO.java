package com.alphatech.alphatech.dto.categoryDto;

import com.alphatech.alphatech.model.Category;

public record CategorySubDTO(
        Long id,
        String categoryName
) {
    public static CategorySubDTO fromEntity(Category category) {
        if (category == null) return null;
        return new CategorySubDTO(
                category.getId(),
                category.getCategoryName()
        );
    }
}
