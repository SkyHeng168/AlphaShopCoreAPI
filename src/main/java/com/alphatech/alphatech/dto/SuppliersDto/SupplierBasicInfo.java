package com.alphatech.alphatech.dto.SuppliersDto;

import com.alphatech.alphatech.model.Suppliers;

public record SupplierBasicInfo(
        Long id,
        String supplierName,
        String email
) {
    public static SupplierBasicInfo fromSupplier(Suppliers supplier) {
        return new SupplierBasicInfo(
                supplier.getId(),
                supplier.getSupplierName(),
                supplier.getEmail()
        );
    }
}