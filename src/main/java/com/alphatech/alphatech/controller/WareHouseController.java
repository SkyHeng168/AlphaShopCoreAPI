package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.dto.WareHouseDto.WareHouseRequest;
import com.alphatech.alphatech.dto.WareHouseDto.WareHouseRespond;
import com.alphatech.alphatech.service.impl.WareHouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WareHouseController {
    private final WareHouseService wareHouseService;

    @PostMapping
    public ResponseEntity<?> createWareHouse(@Valid @RequestBody WareHouseRequest wareHouseRequest) {
        try{
            WareHouseRespond wareHouseRespond = wareHouseService.createWareHouse(wareHouseRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(wareHouseRespond);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<WareHouseRespond>> getAllWareHouse() {
        List<WareHouseRespond> wareHouseResponds = wareHouseService.getAllWareHouse();
        return ResponseEntity.status(HttpStatus.OK).body(wareHouseResponds);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWareHouse(@PathVariable Long id, @Valid @RequestBody WareHouseRequest wareHouseRequest) {
        try{
            WareHouseRespond wareHouseRespond = wareHouseService.updateWareHouse(id, wareHouseRequest);
            return ResponseEntity.status(HttpStatus.OK).body(wareHouseRespond);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWareHouse(@PathVariable Long id) {
        try{
            wareHouseService.deleteWareHouse(id);
            return ResponseEntity.status(HttpStatus.OK).body("Warehouse has been deleted");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
