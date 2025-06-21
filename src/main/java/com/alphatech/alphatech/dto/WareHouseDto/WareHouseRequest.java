package com.alphatech.alphatech.dto.WareHouseDto;

import com.alphatech.alphatech.enums.WareHouseStatus;
import com.alphatech.alphatech.enums.WarehouseCapacityStatus;

public record WareHouseRequest(
        String warehouseName,
        String warehouseCode,
        String location,
        String contactPhone,
        String  managerName,
        String  managerEmail,
        int capacity,
        WarehouseCapacityStatus warehouseCapacityStatus,
        WareHouseStatus status,
        String note
) {
}
