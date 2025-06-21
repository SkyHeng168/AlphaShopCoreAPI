package com.alphatech.alphatech.dto.WareHouseDto;

import com.alphatech.alphatech.dto.ProductDto.ProductRespond;
import com.alphatech.alphatech.enums.WareHouseStatus;
import com.alphatech.alphatech.enums.WarehouseCapacityStatus;
import com.alphatech.alphatech.model.WareHouse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record WareHouseRespond(
        Long id,
        String warehouseName,
        String warehouseCode,
        String location,
        String contactPhone,
        String  managerName,
        String  managerEmail,
        int capacity,
        WarehouseCapacityStatus warehouseCapacityStatus,
        WareHouseStatus status,
        String note,
        String createdDate,
        List<ProductRespond> products
) {
    public static WareHouseRespond convertToWareHouseRespond(WareHouse wareHouse){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss a");
        return new WareHouseRespond(
                wareHouse.getId(),
                wareHouse.getWarehouseName(),
                wareHouse.getWarehouseCode(),
                wareHouse.getLocation(),
                wareHouse.getContactPhone(),
                wareHouse.getManagerName(),
                wareHouse.getManagerEmail(),
                wareHouse.getCapacity(),
                wareHouse.getWarehouseCapacityStatus(),
                wareHouse.getStatus(),
                wareHouse.getNote(),
                wareHouse.getCreatedDate() != null ? formatter.format(wareHouse.getCreatedDate()) : null,
                wareHouse.getProducts() != null ? wareHouse.getProducts().stream()
                        .map(ProductRespond::convertEntityToDto)
                        .collect(Collectors.toList()) : null
        );
    }
}
