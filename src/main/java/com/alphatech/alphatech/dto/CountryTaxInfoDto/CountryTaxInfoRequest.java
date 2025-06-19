package com.alphatech.alphatech.dto.CountryTaxInfoDto;

import jakarta.validation.constraints.NotBlank;

public record CountryTaxInfoRequest(
        @NotBlank(message = "Country Code is required. Ex: KHM, CN, US")
        String countryCode,

        @NotBlank(message = "Country Name is required")
        String countryName,

        @NotBlank(message = "Tax ID Prefix is required")
        String taxIdPrefix,

        @NotBlank(message = "Tax ID Regex is required")
        String taxIdRegex,

        boolean requiresTaxId,

        @NotBlank(message = "Tax ID Example is required")
        String taxIdExample
) {
}
