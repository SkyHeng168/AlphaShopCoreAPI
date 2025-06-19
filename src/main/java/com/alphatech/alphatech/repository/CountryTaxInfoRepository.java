package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.CountryTaxInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryTaxInfoRepository extends JpaRepository<CountryTaxInfo, Long> {
    boolean existsByCountryCodeIgnoreCaseAndCountryNameIgnoreCase(String countryCode, String countryName);
    Optional<CountryTaxInfo> findByCountryCodeIgnoreCase(String countryCode);
}
