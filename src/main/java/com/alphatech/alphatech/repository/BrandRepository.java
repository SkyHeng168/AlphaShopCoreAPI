package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    boolean existsByBrandNameIgnoreCase(String brandName);
}
