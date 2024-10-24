package com.fsa.cursus.service.Impl;
import com.fsa.cursus.model.entity.Category;
import com.fsa.cursus.model.request.CategoryRequest;
import com.fsa.cursus.repository.CategoryRepository;
import com.fsa.cursus.service.CategoryService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(CategoryRequest categoryRequest) {
        Category category = null;

        if (categoryRequest.getCategoryId() == 0) {
            category = new Category();
        }else {
            category = categoryRepository.getOne(categoryRequest.getCategoryId());
        }

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setCategoryStatus(categoryRequest.getCategoryStatus());

        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {

        Category category = categoryRepository.findById(id).orElse(null);

        categoryRepository.delete(category);
    }
}
