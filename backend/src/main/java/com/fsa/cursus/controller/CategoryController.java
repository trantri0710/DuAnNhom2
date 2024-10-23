package com.fsa.cursus.controller;


import com.fsa.cursus.model.entity.Category;
import com.fsa.cursus.model.mapper.CategoryMapper;
import com.fsa.cursus.model.request.CategoryRequest;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
