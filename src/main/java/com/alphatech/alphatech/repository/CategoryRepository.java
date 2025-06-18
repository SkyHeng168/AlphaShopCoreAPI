package com.alphatech.alphatech.repository;

import com.alphatech.alphatech.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoriesByCategoryNameIgnoreCase(String categoryName);
}
