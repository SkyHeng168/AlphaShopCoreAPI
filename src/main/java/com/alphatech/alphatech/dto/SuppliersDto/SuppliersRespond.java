package com.alphatech.alphatech.dto.SuppliersDto;

import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRespond;
import com.alphatech.alphatech.model.Suppliers;

import java.time.LocalDateTime;

public record SuppliersRespond(
        String supplierName,
        String imageSupplier,
        String email,
        String phoneNumber,
        String address,
        String stressAdd,
        String city,
        String state,
        String zipCode,
        String contactPerson,
        LocalDateTime contractDate,
        CountryTaxInfoRespond countryTaxInfo
) {
    public static SuppliersRespond convertObjectToDto(Suppliers supplier) {
        return new SuppliersRespond(
                supplier.getSupplierName(),
                supplier.getImageSuppliers(),
                supplier.getEmail(),
                supplier.getPhoneNumber(),
                supplier.getAddress(),
                supplier.getStreetAddress(),
                supplier.getCity(),
                supplier.getState(),
                supplier.getZipCode(),
                supplier.getContactPerson(),
                supplier.getContractDate(),
                supplier.getCountryTaxInfo() != null
                        ? CountryTaxInfoRespond.fromEntity(supplier.getCountryTaxInfo())
                        : null
        );
    }
}
