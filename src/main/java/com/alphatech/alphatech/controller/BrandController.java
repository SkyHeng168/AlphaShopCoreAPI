package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.brandDto.BrandRequest;
import com.alphatech.alphatech.dto.brandDto.BrandRespond;
import com.alphatech.alphatech.service.impl.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandRespond>> getAllBrands() {
        List<BrandRespond> brands = brandService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandRespond> getBrandById(@PathVariable Long id){
        try{
            BrandRespond brand = brandService.getBrandById(id);
            return ResponseEntity.status(HttpStatus.OK).body(brand);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBrand(@ModelAttribute BrandRequest brandRequest) {
        try {
            BrandRespond brand = brandService.createBrand(brandRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(brand);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while saving brand logo.");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    @PostMapping(value = "/bulk",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BrandRespond>> createMultipleBrands(@RequestBody List<BrandRequest> brandRequests) {
        try{
            List<BrandRespond> brands = brandService.createMultipleBrands(brandRequests);
            return ResponseEntity.status(HttpStatus.CREATED).body(brands);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping(value = "/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBrand(@PathVariable Long id, BrandRequest brandRequest){
        try {
            BrandRespond brand = brandService.updateBrand(id, brandRequest);
            return ResponseEntity.status(HttpStatus.OK).body(brand);
        }catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id){
        try{
            brandService.deleteBrand(id);
            return ResponseEntity.ok("Brand deleted successfully.");
        }catch (ResourceNotFoundException resourceNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
