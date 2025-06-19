package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.brandDto.BrandRequest;
import com.alphatech.alphatech.dto.brandDto.BrandRespond;

import java.io.IOException;
import java.util.List;

public interface IBrandService {
    BrandRespond createBrand(BrandRequest brandRequest) throws IOException;
    List<BrandRespond> findAll();
    BrandRespond getBrandById(Long id);
    BrandRespond updateBrand(Long id, BrandRequest brandRequest) throws IOException;
    void deleteBrand(Long id) throws IOException;

    List<BrandRespond> createMultipleBrands(List<BrandRequest> brandRequest) throws IOException;
}
