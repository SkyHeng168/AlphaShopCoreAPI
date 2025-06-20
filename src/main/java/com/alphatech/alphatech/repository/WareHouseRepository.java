package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
    boolean existsWareHouseByWarehouseNameIgnoreCase(String warehouseName);
    boolean existsWareHouseByWarehouseNameAndWarehouseCodeIgnoreCase(String warehouseName, String warehouseCode);
}
