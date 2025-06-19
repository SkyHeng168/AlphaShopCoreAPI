package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRequest;
import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRespond;

import java.util.List;

public interface ICountryTaxInfoService {
    CountryTaxInfoRespond createCountryTaxInfo(CountryTaxInfoRequest countryTaxInfoRequest);
    List<CountryTaxInfoRespond> findAll();
    CountryTaxInfoRespond findById(Long id);
    CountryTaxInfoRespond updateCountryTaxInfo(Long id, CountryTaxInfoRequest countryTaxInfoRequest);
    void deleteCountryTaxInfo(Long id);
}
