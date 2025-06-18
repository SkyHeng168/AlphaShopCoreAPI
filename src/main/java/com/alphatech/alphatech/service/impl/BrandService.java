package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.ResourceNotFoundException;
import com.alphatech.alphatech.dto.brandDto.BrandRequest;
import com.alphatech.alphatech.dto.brandDto.BrandRespond;
import com.alphatech.alphatech.model.Brand;
import com.alphatech.alphatech.repository.BrandRepository;
import com.alphatech.alphatech.service.IBrandService;
import com.alphatech.alphatech.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;

    @Override
    public BrandRespond createBrand(BrandRequest brandRequest) throws IOException {
        if (brandRepository.existsByBrandNameIgnoreCase(brandRequest.brandName())) {
            throw new ResourceNotFoundException("Brand with name " + brandRequest.brandName() + " already exists.");
        }
        Brand brand = new Brand();
        populateBrandFields(brand, brandRequest);
        Brand savedBrand = brandRepository.save(brand);
        return BrandRespond.fromEntity(savedBrand);
    }

    @Override
    public List<BrandRespond> findAll() {
        List<Brand> brands = brandRepository.findAll();
        if (brands.isEmpty()) {
            throw new ResourceNotFoundException("No brands available.");
        }
        return brands.stream()
                .map(BrandRespond::fromEntity)
                .toList();
    }

    @Override
    public BrandRespond updateBrand(Long id, BrandRequest brandRequest) throws IOException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + id + " not found."));
        boolean exists = brandRepository.existsByBrandNameIgnoreCase(brandRequest.brandName());
        if (exists && !brand.getBrandName().equalsIgnoreCase(brandRequest.brandName())) {
            throw new ResourceNotFoundException("Brand with name " + brandRequest.brandName() + " already exists.");
        }
        populateBrandFields(brand, brandRequest);
        Brand updatedBrand = brandRepository.save(brand);
        return BrandRespond.fromEntity(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) throws IOException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + id + " not found."));
        if (brand.getLogoUrl() != null && !brand.getLogoUrl().isEmpty()) {
            String logoUrl = "uploads/BrandLogos";
            Path path = Paths.get(logoUrl).resolve(brand.getLogoUrl());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandRespond> createMultipleBrands(List<BrandRequest> brandRequest) throws IOException {
        List<BrandRespond> brandResponds = new ArrayList<>();
        for (BrandRequest brandMulti : brandRequest) {
            if (brandRepository.existsByBrandNameIgnoreCase(brandMulti.brandName())) {
                throw new ResourceNotFoundException("Brand with name " + brandMulti.brandName() + " already exists.");
            }
            Brand brand = new Brand();
            populateBrandFields(brand, brandMulti);
            Brand savedBrand = brandRepository.save(brand);
            brandResponds.add(BrandRespond.fromEntity(savedBrand));
        }
        if (brandResponds.isEmpty()) {
            throw new ResourceNotFoundException("No brands available.");
        }
        return brandResponds;
    }

    private void populateBrandFields(Brand brand, BrandRequest brandRequest) throws IOException {
        brand.setBrandName(brandRequest.brandName());
        brand.setDescription(brandRequest.description());
        brand.setWebsiteUrl(brandRequest.websiteUrl());
        brand.setSocialMediaUrl(brandRequest.socialMediaUrl());
        brand.setCountry(brandRequest.country());
        brand.setBrandStatus(brandRequest.brandStatus());
        brand.setUpdatedDate(LocalDateTime.now());
        if (brandRequest.logoUrl() != null && !brandRequest.logoUrl().isEmpty()) {
            String savedLogo = saveLogoFile(brandRequest.logoUrl());
            brand.setLogoUrl(savedLogo);
        }
    }
    private String saveLogoFile(MultipartFile file) throws IOException {
        return FileUploadUtil.saveFile("uploads/BrandLogos/", file);
    }
}
