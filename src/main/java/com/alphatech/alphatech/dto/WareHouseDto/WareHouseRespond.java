package com.alphatech.alphatech.dto.WareHouseDto;

import com.alphatech.alphatech.enums.WareHouseStatus;
import com.alphatech.alphatech.model.WareHouse;

import java.time.format.DateTimeFormatter;

public record WareHouseRespond(
        String warehouseName,
        String warehouseCode,
        String location,
        String contactPhone,
        String  managerName,
        String  managerEmail,
        int capacity,
        WareHouseStatus status,
        String note,
        String createdDate
) {
    public static WareHouseRespond convertToWareHouseRespond(WareHouse wareHouse){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss a");
        return new WareHouseRespond(
                wareHouse.getWarehouseName(),
                wareHouse.getWarehouseCode(),
                wareHouse.getLocation(),
                wareHouse.getContactPhone(),
                wareHouse.getManagerName(),
                wareHouse.getManagerEmail(),
                wareHouse.getCapacity(),
                wareHouse.getStatus(),
                wareHouse.getNote(),
                wareHouse.getCreatedDate() != null ? formatter.format(wareHouse.getCreatedDate()) : null
        );
    }
}
