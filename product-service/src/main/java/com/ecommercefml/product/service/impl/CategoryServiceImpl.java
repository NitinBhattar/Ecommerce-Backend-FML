package com.ecommercefml.product.service.impl;

import com.ecommercefml.product.dto.CategoryDto;
import com.ecommercefml.product.dto.CreateCategoryDto;
import com.ecommercefml.product.entity.Category;
import com.ecommercefml.product.expection.ResourceAlreadyExistsException;
import com.ecommercefml.product.expection.ResourceNotFoundException;
import com.ecommercefml.product.mapper.CategoryMapper;
import com.ecommercefml.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        // Category already exists
        if (!categoryRepository.existsByName(createCategoryDto.getName())) {
            throw new ResourceAlreadyExistsException("Category already exists");
        }

        Category category = categoryMapper.toEntity(createCategoryDto);
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));

        // Update in repo
        categoryMapper.updateEntity(categoryDto, category);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));

        // Delete
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        // Checking if categoryId is null
        if (id == null) {
            throw new IllegalArgumentException("Category id is missing");
        }

        // Fetch from repo
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryMapper::toDto).toList();
    }
}
