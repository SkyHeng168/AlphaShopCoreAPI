package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.ProductDto.ProductRequest;
import com.alphatech.alphatech.dto.ProductDto.ProductRespond;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    ProductRespond createProduct(ProductRequest  productRequest) throws IOException;
    List<ProductRespond> createMultipleProducts(List<ProductRequest> productRequests) throws IOException;
    List<ProductRespond> findAllByCategory();
    ProductRespond updateProduct(Long id, ProductRequest productRequest) throws IOException;
    void deleteProduct(Long id) throws IOException;

    ProductRespond findById(Long id) throws IOException;
}
