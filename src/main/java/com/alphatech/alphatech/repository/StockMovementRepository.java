package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement,Long> {
    List<StockMovement> findByInventoryId(Long inventoryId);
}
