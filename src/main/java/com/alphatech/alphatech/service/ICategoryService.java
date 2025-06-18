package com.alphatech.alphatech.service;

import com.alphatech.alphatech.dto.categoryDto.CategoryRequest;
import com.alphatech.alphatech.dto.categoryDto.CategoryRespond;

import java.util.List;

public interface ICategoryService {
    CategoryRespond createCategory(CategoryRequest categoryRequest) throws Exception;
    List<CategoryRespond> findAll();
    CategoryRespond updateCategory(Long id, CategoryRequest categoryRequest) throws Exception;
    void deleteCategory(Long id) throws Exception;
}
