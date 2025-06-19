package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRequest;
import com.alphatech.alphatech.dto.SuppliersDto.SuppliersRespond;
import com.alphatech.alphatech.service.impl.SuppliersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SuppliersController {
    private final SuppliersService suppliersService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<SuppliersRespond> getAllSupplierData = suppliersService.findAllSuppliers();
            if (getAllSupplierData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No suppliers found.");
            }
            return ResponseEntity.ok(getAllSupplierData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve suppliers: " + e.getMessage());
        }
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createSuppliers (@ModelAttribute SuppliersRequest suppliersRequest) {
        try {
            SuppliersRespond suppliers = suppliersService.createSuppliers(suppliersRequest);
            return ResponseEntity.status(HttpStatus.OK).body(suppliers);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = ("/{id}"),consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateSuppliers(@PathVariable Long id, SuppliersRequest suppliersRequest){
        try{
            SuppliersRespond suppliersRespond = suppliersService.updateSuppliers(id, suppliersRequest);
            return ResponseEntity.status(HttpStatus.OK).body(suppliersRespond);
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }catch (Exception ioException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ioException.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSuppliers(@PathVariable Long id){
        try{
            suppliersService.deleteSuppliers(id);
            return ResponseEntity.status(HttpStatus.OK).body("Supplier with ID " + id + " deleted successfully.");
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
