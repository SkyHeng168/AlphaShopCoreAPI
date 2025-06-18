package com.alphatech.alphatech.dto.brandDto;

import com.alphatech.alphatech.enums.BrandStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record BrandRequest (
        @NotBlank(message = "Brand name is required")
        @Size(max = 50)
        String brandName,
        MultipartFile logoUrl,
        @Size(max = 1000)
        String description,
        String websiteUrl,
        String socialMediaUrl,
        @NotBlank(message = "Country is required")
        @Size(max = 50)
        String country,
        @NotNull(message = "Brand status is required")
        BrandStatus brandStatus,
        LocalDateTime createDate,
        LocalDateTime updateDate
){
}
