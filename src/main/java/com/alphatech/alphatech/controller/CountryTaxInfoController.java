package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRequest;
import com.alphatech.alphatech.dto.CountryTaxInfoDto.CountryTaxInfoRespond;
import com.alphatech.alphatech.service.impl.CountryTaxInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countryTaxInfo")
@RequiredArgsConstructor
public class CountryTaxInfoController {
    private final CountryTaxInfoService countryTaxInfoService;

    @PostMapping()
    public ResponseEntity<?> createCountryTaxId (@Valid @RequestBody CountryTaxInfoRequest  countryTaxInfoRequest){
        try{
            CountryTaxInfoRespond countryTaxInfoRespond = countryTaxInfoService.createCountryTaxInfo(countryTaxInfoRequest);
            return ResponseEntity.status(HttpStatus.OK).body(countryTaxInfoRespond);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<CountryTaxInfoRespond>> getAllCountryTaxInfo(){
        List<CountryTaxInfoRespond> countryTaxInfoResponds = countryTaxInfoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(countryTaxInfoResponds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryTaxInfoRespond> getCountryTaxInfoById(@PathVariable Long id){
        CountryTaxInfoRespond countryTaxInfoRespond = countryTaxInfoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(countryTaxInfoRespond);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCountryTaxInfo(@PathVariable Long id, @Valid @RequestBody CountryTaxInfoRequest  countryTaxInfoRequest){
        try {
            CountryTaxInfoRespond countryTaxInfoRespond = countryTaxInfoService.updateCountryTaxInfo(id, countryTaxInfoRequest);
            return ResponseEntity.status(HttpStatus.OK).body(countryTaxInfoRespond);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountryTaxInfo(@PathVariable Long id) {
        try {
            countryTaxInfoService.deleteCountryTaxInfo(id);
            return ResponseEntity.ok("Country Tax Info deleted successfully!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

}
