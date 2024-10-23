package com.fsa.cursus.model.mapper;


import com.fsa.cursus.model.entity.Category;
import com.fsa.cursus.model.request.CategoryRequest;
import com.fsa.cursus.model.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toCategoryRequest(Category category) {
        if (category == null) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setCategoryStatus(category.getCategoryStatus());
        return categoryResponse;
    }
}
