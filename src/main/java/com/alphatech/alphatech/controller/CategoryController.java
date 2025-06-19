package com.alphatech.alphatech.controller;

import com.alphatech.alphatech.Exception.customException.ResourceNotFoundException;
import com.alphatech.alphatech.dto.categoryDto.CategoryRequest;
import com.alphatech.alphatech.dto.categoryDto.CategoryRespond;
import com.alphatech.alphatech.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryRespond>> getAllCategories(){
        List<CategoryRespond> categories = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCategory(@ModelAttribute CategoryRequest categoryRequest) {
        try{
            CategoryRespond categoryRespond = categoryService.createCategory(categoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(categoryRespond);
        }catch (IOException e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("Error occurred while saving brand logo.");
        } catch (ResourceNotFoundException resourceNotFoundException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resourceNotFoundException.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, CategoryRequest categoryRequest){
        try{
            CategoryRespond categoryRespond = categoryService.updateCategory(id, categoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(categoryRespond);
        }catch (ResourceNotFoundException resourceNotFoundException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resourceNotFoundException.getMessage());
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while saving brand logo." + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully.");
        } catch (ResourceNotFoundException resourceNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resourceNotFoundException.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving brand logo." + e.getMessage());
        }
    }
}
