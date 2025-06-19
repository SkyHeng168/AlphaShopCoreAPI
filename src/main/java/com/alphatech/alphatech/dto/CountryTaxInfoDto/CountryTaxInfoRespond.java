package com.alphatech.alphatech.dto.CountryTaxInfoDto;

import com.alphatech.alphatech.model.CountryTaxInfo;

public record CountryTaxInfoRespond(
        String countryCode,
        String countryName,
        String taxIdPrefix,
        String taxIdRegex,
        boolean requiresTaxId,
        String taxIdExample
) {
    public static CountryTaxInfoRespond fromEntity(CountryTaxInfo countryTaxInfo) {
        return new CountryTaxInfoRespond(
                countryTaxInfo.getCountryCode(),
                countryTaxInfo.getCountryName(),
                countryTaxInfo.getTaxIdPrefix(),
                countryTaxInfo.getTaxIdRegex(),
                countryTaxInfo.isRequiresTaxId(),
                countryTaxInfo.getTaxIdExample()
        );
    }
}
