package com.fsa.cursus.service;

import com.fsa.cursus.model.entity.Category;
import com.fsa.cursus.model.request.CategoryRequest;

public interface CategoryService {

    Category saveCategory(CategoryRequest categoryRequest);

    Category getCategoryById(int id);

    Category updateCategory(Category category);

    void deleteCategory(int id);
}
