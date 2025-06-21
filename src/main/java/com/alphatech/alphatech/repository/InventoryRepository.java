package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.warehouse.id = :warehouseId")
    int sumQuantityByWareHouse(@Param("warehouseId") Long warehouseId);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Inventory i WHERE i.product.id = :productId AND i.warehouse.id = :warehouseId")
    boolean existsByProductIdAndWarehouseId(@Param("productId") Long productId, @Param("warehouseId") Long warehouseId);

    boolean existsByProductId(Long product);
}
