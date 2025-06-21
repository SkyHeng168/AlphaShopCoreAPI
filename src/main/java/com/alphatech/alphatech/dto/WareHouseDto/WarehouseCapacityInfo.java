package com.alphatech.alphatech.dto.WareHouseDto;

import com.alphatech.alphatech.enums.WarehouseCapacityStatus;

public record WarehouseCapacityInfo(
    Long warehouseId,
    int capacity,
    int usedCapacity,
    int availableCapacity,
    WarehouseCapacityStatus capacityStatus,
    double usagePercentage
) {}
