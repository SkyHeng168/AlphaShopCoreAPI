package com.alphatech.alphatech.dto.SuppliersDto;

import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRespond;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record SuppliersRequest(
        String supplierName,
        MultipartFile imageSuppliers,
        String email,
        String phoneNumber,
        String address,
        String streetAddress,
        String city,
        String state,
        String zipCode,
        String contactPerson,
        LocalDateTime contractDate,
        String countryTaxInfo,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
