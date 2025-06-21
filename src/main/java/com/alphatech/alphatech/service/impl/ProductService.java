package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceAlreadyExistsException;
import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.ProductDto.ProductRequest;
import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.enums.ProductStatus;
import com.alphatech.alphatech.model.*;
import com.alphatech.alphatech.repository.*;
import com.alphatech.alphatech.service.IProductService;
import com.alphatech.alphatech.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final WareHouseRepository wareHouseRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SuppliersRepository suppliersRepository;

    @Override
    public ProductRespond createProduct(ProductRequest request) throws IOException {
        if (productRepository.existsProductByProductNameContainingIgnoreCase(request.productName())) {
            throw new ResourceAlreadyExistsException("Product: " + request.productName() + " already exists");
        }

        Product product = buildProductEntity(request);
        Product savedProduct = productRepository.save(product);
        return ProductRespond.convertEntityToDto(savedProduct);
    }

    @Override
    public List<ProductRespond> createMultipleProducts(List<ProductRequest> productRequests) throws IOException {
        List<Product> productsToSave = new ArrayList<>();

        for (ProductRequest productRequest : productRequests) {
            if (productRepository.existsProductByProductNameContainingIgnoreCase(productRequest.productName())) {
                throw new ResourceAlreadyExistsException("Product: " + productRequest.productName() + " already exists");
            }

            Product product = buildProductEntity(productRequest);
            productsToSave.add(product);
        }

        if (productsToSave.isEmpty()) {
            throw new ResourceNotFoundException("No products found to save.");
        }

        List<Product> savedProducts = productRepository.saveAll(productsToSave);
        return savedProducts.stream()
                .map(ProductRespond::convertEntityToDto)
                .toList();
    }

    @Override
    public List<ProductRespond> findAllByCategory() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        return products.stream()
                .map(ProductRespond::convertEntityToDto)
                .toList();
    }

    @Override
    public ProductRespond updateProduct(Long id, ProductRequest request) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product ID: " + id + " not found"));

        boolean exists = productRepository.existsByProductNameIgnoreCaseAndIdNot(request.productName(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Product: " + request.productName() + " already exists");
        }

        String imageName = handleProductImage(request.imageUrl());
        if (imageName != null) {
            product.setImageUrl(imageName);
        }

        product.setProductName(request.productName());
        product.setDescription(request.description());
        product.setCurrentPrice(request.currentPrice());
        product.setSku(request.sku());
        product.setFeature(request.isFeature());
        product.setUpdatedDate(LocalDateTime.now());
        loadAndSetAssociations(product, request);

        Product updatedProduct = productRepository.save(product);
        return ProductRespond.convertEntityToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product ID: " + id + " not found"));
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            String logoUrl = "uploads/Product/";
            Path path = Paths.get(logoUrl).resolve(product.getImageUrl());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
        product.setWareHouse(null);
        product.setCategory(null);
        product.setBrand(null);
        product.setSuppliers(null);
        productRepository.save(product);
        productRepository.delete(product);
    }

    @Override
    public ProductRespond findById(Long id) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product ID: " + id + " not found"));
        return ProductRespond.convertEntityToDto(product);
    }

    private void saveImageFile(MultipartFile file) throws IOException {
        FileUploadUtil.saveFile("uploads/Product", file);
    }

    private void loadAndSetAssociations(Product product, ProductRequest request) {
        WareHouse wareHouse = wareHouseRepository.findById(request.wareHouse())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
        Category category = categoryRepository.findById(request.category())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Brand brand = brandRepository.findById(request.brand())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        Suppliers supplier = suppliersRepository.findById(request.supplier())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        ProductStatus status = ProductStatus.valueOf(request.status().toUpperCase());

        product.setWareHouse(wareHouse);
        product.setCategory(category);
        product.setBrand(brand);
        product.setSuppliers(supplier);
        product.setStatus(status);

    }

    private Product buildProductEntity(ProductRequest request) throws IOException {
        String imageName = handleProductImage(request.imageUrl());

        Product product = Product.builder()
                .productName(request.productName())
                .description(request.description())
                .basePrice(null)
                .currentPrice(request.currentPrice())
                .imageUrl(imageName)
                .sku(request.sku())
                .isFeature(request.isFeature())
                .createdDate(LocalDateTime.now())
                .build();

        loadAndSetAssociations(product, request);
        return product;
    }

    private String handleProductImage(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            saveImageFile(imageFile); // your existing logic
            return imageName;
        }
        return null;
    }
}