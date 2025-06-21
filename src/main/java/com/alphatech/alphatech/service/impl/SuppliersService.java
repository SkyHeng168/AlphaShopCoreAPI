package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceAlreadyExistsException;
import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRequest;
import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRespond;
import com.alphatech.alphatech.model.CountryTaxInfo;
import com.alphatech.alphatech.model.Product;
import com.alphatech.alphatech.model.Suppliers;
import com.alphatech.alphatech.repository.CountryTaxInfoRepository;
import com.alphatech.alphatech.repository.ProductRepository;
import com.alphatech.alphatech.repository.SuppliersRepository;
import com.alphatech.alphatech.service.ISuppliersService;
import com.alphatech.alphatech.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SuppliersService implements ISuppliersService {
    private final SuppliersRepository suppliersRepository;
    private final CountryTaxInfoRepository countryTaxInfoRepository;
    private final ProductRepository productRepository;
    @Override
    public List<SuppliersRespond> findAllSuppliers() {
        List<Suppliers> suppliersList = suppliersRepository.findAll();

        if (suppliersList.isEmpty()) {
            throw new ResourceNotFoundException("No suppliers found.");
        }

        return suppliersList.stream()
                .map(SuppliersRespond::convertObjectToDto)
                .toList();
    }

    @Override
    public SuppliersRespond createSuppliers(SuppliersRequest suppliersRequest) {
        try {
            if (suppliersRepository.existsBySupplierNameIgnoreCaseAndEmailIgnoreCase(
                    suppliersRequest.supplierName(), suppliersRequest.email())) {
                throw new ResourceAlreadyExistsException("Supplier already exists.");
            }

            CountryTaxInfo country = countryTaxInfoRepository.findByCountryCodeIgnoreCase(
                            suppliersRequest.countryTaxInfo())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found."));

            Suppliers suppliers = mapToEntity(suppliersRequest, country, null);
            Suppliers saved = suppliersRepository.save(suppliers);

            return SuppliersRespond.convertObjectToDto(saved);

        } catch (Exception ex) {
            throw new RuntimeException("Unexpected error while creating supplier: " + ex.getMessage());
        }
    }

    @Override
    public SuppliersRespond updateSuppliers(Long id, SuppliersRequest suppliersRequest) throws Exception {
        Suppliers existingSupplier = suppliersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID " + id + " not found."));

        boolean exists = suppliersRepository.existsBySupplierNameIgnoreCaseAndEmailIgnoreCase(
                suppliersRequest.supplierName(), suppliersRequest.email());

        if (exists && (!existingSupplier.getSupplierName().equalsIgnoreCase(suppliersRequest.supplierName()) ||
                !existingSupplier.getEmail().equalsIgnoreCase(suppliersRequest.email()))) {
            throw new ResourceAlreadyExistsException("Supplier: " + suppliersRequest.supplierName() +
                    " and Email: " + suppliersRequest.email() + " already exist.");
        }

        if (existingSupplier.getImageSuppliers() != null && !existingSupplier.getImageSuppliers().isEmpty()) {
            String oldImagePath = "uploads/suppliers/" + existingSupplier.getImageSuppliers();
            File oldImageFile = new File(oldImagePath);
            if (oldImageFile.exists()) {
                boolean deleted = oldImageFile.delete();
                System.out.println("Old image deletion status: " + deleted);
            } else {
                System.out.println("Old image file not found: " + oldImagePath);
            }
        }


        CountryTaxInfo countryTaxInfo = countryTaxInfoRepository.findByCountryCodeIgnoreCase(suppliersRequest.countryTaxInfo())
                .orElseThrow(() -> new ResourceNotFoundException("Country with code '" + suppliersRequest.countryTaxInfo() + "' not found."));

        Suppliers updateSuppliers = mapToEntity(suppliersRequest, countryTaxInfo, existingSupplier);

        Suppliers saved = suppliersRepository.save(updateSuppliers);
        return SuppliersRespond.convertObjectToDto(saved);
    }

    @Override
    public void deleteSuppliers(Long id) throws IOException {
        Suppliers suppliers = suppliersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID " + id + " not found."));

        if (suppliers.getImageSuppliers() != null && !suppliers.getImageSuppliers().isEmpty()) {
            String ImageSupplierPath = "uploads/suppliers/";
            Path path = Paths.get(ImageSupplierPath).resolve(suppliers.getImageSuppliers());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
        for (Product product : suppliers.getProducts()) {
            product.setSuppliers(null);
            productRepository.save(product);
        }
        suppliersRepository.deleteById(id);
    }

    private Suppliers mapToEntity(SuppliersRequest request, CountryTaxInfo country, Suppliers existingSupplier) throws IOException {
        return Suppliers.builder()
                .id(existingSupplier != null ? existingSupplier.getId() : null)
                .supplierName(request.supplierName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .imageSuppliers(SaveImage(request.imageSuppliers()))
                .address(request.address())
                .streetAddress(request.streetAddress())
                .city(request.city())
                .state(request.state())
                .zipCode(request.zipCode())
                .contactPerson(request.contactPerson())
                .contractDate(request.contractDate())
                .countryTaxInfo(country)
                .createdDate(existingSupplier != null ? existingSupplier.getCreatedDate() : LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    private String SaveImage(MultipartFile multipartFile) throws IOException {
        return FileUploadUtil.saveFile("uploads/suppliers", multipartFile);
    }

}
