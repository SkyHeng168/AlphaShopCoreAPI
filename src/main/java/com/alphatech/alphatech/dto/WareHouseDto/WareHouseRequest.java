package com.alphatech.alphatech.dto.WareHouseDto;

import com.alphatech.alphatech.enums.WareHouseStatus;

public record WareHouseRequest(
        String warehouseName,
        String warehouseCode,
        String location,
        String contactPhone,
        String  managerName,
        String  managerEmail,
        int capacity,
        WareHouseStatus status,
        String note
) {
}
