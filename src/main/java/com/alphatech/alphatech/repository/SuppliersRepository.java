package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.CountryTaxInfo;
import com.alphatech.alphatech.model.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuppliersRepository extends JpaRepository<Suppliers, Long> {
    boolean existsBySupplierNameIgnoreCaseAndEmailIgnoreCase(String supplierName, String email);

    List<Suppliers> findByCountryTaxInfo(CountryTaxInfo countryTaxInfo);
}
