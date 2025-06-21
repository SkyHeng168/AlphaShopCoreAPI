package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsProductByProductNameContainingIgnoreCase(String productName);
    boolean existsByProductNameIgnoreCaseAndIdNot(String s, Long id);
}
