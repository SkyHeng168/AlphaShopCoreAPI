package com.alphatech.alphatech.dto.brandDto;

import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.enums.BrandStatus;
import com.alphatech.alphatech.model.Brand;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record BrandRespond(
        Long id,
        String brandName,
        String logoUrl,
        String description,
        String websiteUrl,
        String socialMediaUrl,
        String country,
        BrandStatus brandStatus,
        String createDate,
        String updateDate,
        List<ProductRespond> product
) {
    public static BrandRespond fromEntity(Brand b) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
        return new BrandRespond(
                b.getId(),
                b.getBrandName(),
                b.getLogoUrl(),
                b.getDescription(),
                b.getWebsiteUrl(),
                b.getSocialMediaUrl(),
                b.getCountry(),
                b.getBrandStatus(),
                b.getCreatedDate() != null ? b.getCreatedDate().format(formatter) : null,
                b.getUpdatedDate() != null ? b.getUpdatedDate().format(formatter) : null,
                b.getProducts() != null ? b.getProducts().stream()
                        .map(ProductRespond::convertEntityToDto)
                        .collect(Collectors.toList()) : null
        );
    }
}
