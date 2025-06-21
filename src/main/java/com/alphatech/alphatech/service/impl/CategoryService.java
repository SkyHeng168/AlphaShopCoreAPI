package com.alphatech.alphatech.service.impl;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.categoryDto.CategoryRequest;
import com.alphatech.alphatech.dto.categoryDto.CategoryRespond;
import com.alphatech.alphatech.model.Category;
import com.alphatech.alphatech.model.Product;
import com.alphatech.alphatech.repository.CategoryRepository;
import com.alphatech.alphatech.repository.ProductRepository;
import com.alphatech.alphatech.service.ICategoryService;
import com.alphatech.alphatech.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    public CategoryRespond createCategory(CategoryRequest categoryRequest) throws Exception {
        if (categoryRepository.existsCategoriesByCategoryNameIgnoreCase(categoryRequest.categoryName())) {
            throw new ResourceNotFoundException("Category with name " + categoryRequest.categoryName() + " already exists.");
        }
        Category category = new Category();
        categoryPopulate(category, categoryRequest);
        Category categorySaved = categoryRepository.save(category);
        return CategoryRespond.fromEntity(categorySaved);
    }

    @Override
    public List<CategoryRespond> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("Category Not Found");
        }
        return categories.stream()
                .map(CategoryRespond::fromEntity)
                .toList();
    }

    @Override
    public CategoryRespond updateCategory(Long id, CategoryRequest categoryRequest) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found."));
        boolean exits = categoryRepository.existsCategoriesByCategoryNameIgnoreCase(categoryRequest.categoryName());
        if (exits && !category.getCategoryName().equalsIgnoreCase(categoryRequest.categoryName())) {
            throw new ResourceNotFoundException("Category with name " + categoryRequest.categoryName() + " already exists.");
        }
        categoryPopulate(category, categoryRequest);
        Category categorySaved = categoryRepository.save(category);
        return CategoryRespond.fromEntity(categorySaved);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found."));
        if (category.getCategoryLogo() != null && !category.getCategoryLogo().isEmpty()){
            String logoPath = "uploads/CategoryLogos/";
            Path path = Paths.get(logoPath).resolve(category.getCategoryLogo());
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
        for (Product product : category.getProducts()) {
            product.setCategory(null);
            productRepository.save(product);
        }
        categoryRepository.deleteById(id);
    }

    private void categoryPopulate(Category category, CategoryRequest categoryRequest) throws IOException {
        category.setCategoryName(categoryRequest.categoryName());
        category.setDescription(categoryRequest.description());
        category.setCategoryStatus(categoryRequest.categoryStatus());
        category.setCreatedDate(LocalDateTime.now());

        if (categoryRequest.categoryLogo() != null && !categoryRequest.categoryLogo().isEmpty()) {
            String savedFile = saveCategories(categoryRequest.categoryLogo());
            category.setCategoryLogo(savedFile);
        }

        if (categoryRequest.subCategoryId() != null) {
            Category subCategory = categoryRepository.findById(categoryRequest.subCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Sub-category not found with ID: " + categoryRequest.subCategoryId()));
            category.setSubCategory(subCategory);
        } else {
            category.setSubCategory(null); // Optional: clear it
        }
    }

    private String saveCategories(MultipartFile file) throws IOException {
        return FileUploadUtil.saveFile("uploads/CategoryLogos/", file);
    }
}
