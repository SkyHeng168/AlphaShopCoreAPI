package com.alphatech.alphatech.dto.categoryDto;

import com.alphatech.alphatech.enums.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        String categoryName,
        MultipartFile categoryLogo,
        String description,
        @NotNull(message = "Status is required")
        CategoryStatus categoryStatus,
        Long subCategoryId,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
