package com.alphatech.alphatech.service.impl;


import com.alphatech.alphatech.Exception.customException.ResourceAlreadyExistsException;
import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRequest;
import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRespond;
import com.alphatech.alphatech.model.CountryTaxInfo;
import com.alphatech.alphatech.model.Suppliers;
import com.alphatech.alphatech.repository.CountryTaxInfoRepository;
import com.alphatech.alphatech.repository.SuppliersRepository;
import com.alphatech.alphatech.service.ICountryTaxInfoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CountryTaxInfoService implements ICountryTaxInfoService {
    private final CountryTaxInfoRepository countryTaxInfoRepository;
    private final SuppliersRepository suppliersRepository;
    @Override
    public CountryTaxInfoRespond createCountryTaxInfo(CountryTaxInfoRequest countryTaxInfoRequest) {
        if (countryTaxInfoRepository.existsByCountryCodeIgnoreCaseAndCountryNameIgnoreCase(countryTaxInfoRequest.countryCode(),countryTaxInfoRequest.countryName())) {
            throw new ResourceAlreadyExistsException("Country Code and Country Name Already Exists");
        }
        CountryTaxInfo countryTaxInfo = CountryTaxInfo.builder()
                .countryCode(countryTaxInfoRequest.countryCode())
                .countryName(countryTaxInfoRequest.countryName())
                .taxIdPrefix(countryTaxInfoRequest.taxIdPrefix())
                .taxIdRegex(countryTaxInfoRequest.taxIdRegex())
                .requiresTaxId(true)
                .taxIdExample(countryTaxInfoRequest.taxIdExample())
                .build();
        CountryTaxInfo countryTaxInfoSaved = countryTaxInfoRepository.save(countryTaxInfo);
        return CountryTaxInfoRespond.fromEntity(countryTaxInfoSaved);
    }

    @Override
    public List<CountryTaxInfoRespond> findAll() {
        List<CountryTaxInfo> taxIdList = countryTaxInfoRepository.findAll();
        if (taxIdList.isEmpty()) {
            throw new ResourceNotFoundException("Country Tax Info Not Found");
        }
        return taxIdList.stream()
                .map(CountryTaxInfoRespond::fromEntity)
                .toList();
    }

    @Override
    public CountryTaxInfoRespond findById(Long id) {
        CountryTaxInfo countryTaxInfo = countryTaxInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country Tax Info Not Found"));
        return CountryTaxInfoRespond.fromEntity(countryTaxInfo);
    }

    @Override
    public CountryTaxInfoRespond updateCountryTaxInfo(Long id, CountryTaxInfoRequest countryTaxInfoRequest) {
        CountryTaxInfo countryTaxInfo = countryTaxInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country Tax Info Not Found"));

        boolean existsCountryTax = countryTaxInfoRepository
                .existsByCountryCodeIgnoreCaseAndCountryNameIgnoreCase(countryTaxInfoRequest.countryCode(), countryTaxInfoRequest.countryName());

        if (existsCountryTax &&
                (!countryTaxInfo.getCountryCode().equalsIgnoreCase(countryTaxInfoRequest.countryCode()) ||
                        !countryTaxInfo.getCountryName().equalsIgnoreCase(countryTaxInfoRequest.countryName()))) {
            throw new ResourceAlreadyExistsException("Country Code and Country Name Already Exists");
        }

        countryTaxInfo.setCountryCode(countryTaxInfoRequest.countryCode());
        countryTaxInfo.setCountryName(countryTaxInfoRequest.countryName());
        countryTaxInfo.setTaxIdPrefix(countryTaxInfoRequest.taxIdPrefix());
        countryTaxInfo.setTaxIdRegex(countryTaxInfoRequest.taxIdRegex());
        countryTaxInfo.setRequiresTaxId(countryTaxInfoRequest.requiresTaxId());
        countryTaxInfo.setTaxIdExample(countryTaxInfoRequest.taxIdExample());

        CountryTaxInfo updated = countryTaxInfoRepository.save(countryTaxInfo);
        return CountryTaxInfoRespond.fromEntity(updated);
    }

    @Override
    public void deleteCountryTaxInfo(Long id) {
        CountryTaxInfo countryTaxInfo = countryTaxInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country Tax Info Not Found"));
        List<Suppliers> suppliers = suppliersRepository.findByCountryTaxInfo((countryTaxInfo));
        for (Suppliers supplier : suppliers) {
            supplier.setCountryTaxInfo(null);
        }
        suppliersRepository.saveAll(suppliers);
        countryTaxInfoRepository.delete(countryTaxInfo);
    }

}