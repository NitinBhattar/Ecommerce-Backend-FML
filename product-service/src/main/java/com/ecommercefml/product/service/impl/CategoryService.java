package com.ecommercefml.product.service.impl;

import com.ecommercefml.product.dto.CategoryDto;
import com.ecommercefml.product.dto.CreateCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryDto createCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories();
}
