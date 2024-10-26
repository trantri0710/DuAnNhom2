package com.fsa.cursus.controller;


import com.fsa.cursus.model.entity.Category;
import com.fsa.cursus.model.mapper.CategoryMapper;
import com.fsa.cursus.model.request.CategoryRequest;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody CategoryRequest categoryRequest) {

        Category category = categoryService.saveCategory(categoryRequest);

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.ok("ok", categoryMapper.toCategoryRequest(category));

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable int id, @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.getCategoryById(id);

        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        } else {

            ApiResponse apiResponse = new ApiResponse();

            updatedCategory.setCategoryName(categoryRequest.getCategoryName());
            updatedCategory.setCategoryStatus(categoryRequest.getCategoryStatus());

            categoryService.updateCategory(updatedCategory);

            apiResponse.ok("ok", categoryMapper.toCategoryRequest(updatedCategory));

            return ResponseEntity.ok(apiResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id) {
        ApiResponse apiResponse = new ApiResponse();

        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }else {
            category.setCategoryStatus(false);
            categoryService.deleteCategory(id);
            apiResponse.ok("ok", categoryMapper.toCategoryRequest(category));
            return ResponseEntity.ok(apiResponse);
        }
    }

}
