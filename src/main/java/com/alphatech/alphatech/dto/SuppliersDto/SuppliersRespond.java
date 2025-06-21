package com.alphatech.alphatech.dto.SuppliersDto;

import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRespond;
import com.alphatech.alphatech.dto.DocumentDto.DocumentRespond;
import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.model.Suppliers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SuppliersRespond(
        Long id,
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
        CountryTaxInfoRespond countryTaxInfo,
        List<DocumentRespond> documentContracts,
        List<ProductRespond> product
) {
    public static SuppliersRespond convertObjectToDto(Suppliers supplier) {
        return new SuppliersRespond(
                supplier.getId(),
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
                        : null,
                supplier.getDocumentContracts() != null
                        ? supplier.getDocumentContracts().stream()
                        .map(DocumentRespond::toDocumentRespond)
                        .collect(Collectors.toList())
                        : null,
                supplier.getProducts() != null
                        ? supplier.getProducts().stream()
                        .map(ProductRespond::convertEntityToDto)
                        .collect(Collectors.toList())
                        :null
        );
    }
}