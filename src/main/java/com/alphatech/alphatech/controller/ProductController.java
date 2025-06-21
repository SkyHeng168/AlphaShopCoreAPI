package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.dto.ProductDto.ProductRequest;
import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductRespond>> findAll() {
        List<ProductRespond> productResponds = productService.findAllByCategory();
        return ResponseEntity.status(HttpStatus.OK).body(productResponds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRespond> getProductById(@PathVariable Long id) throws IOException {
        ProductRespond productRespond = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productRespond);
    }

    @PostMapping
    public ResponseEntity<ProductRespond> createProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<ProductRespond>> createMultipleProducts(@RequestBody List<ProductRequest> productRequests) throws IOException {
        List<ProductRespond> productResponds = productService.createMultipleProducts(productRequests);
        return ResponseEntity.status(HttpStatus.OK).body(productResponds);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRespond> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequest productRequest) throws IOException {
        ProductRespond updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

