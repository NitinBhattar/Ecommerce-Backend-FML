package com.ecommercefml.product.mapper;

import com.ecommercefml.product.dto.CategoryDto;
import com.ecommercefml.product.dto.CreateCategoryDto;
import com.ecommercefml.product.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// componentModel = "spring" means making it a spring bean
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryDto createCategoryDto);
    Category toEntity(CategoryDto categoryDto);

    @Mapping(target = "id", ignore = true)
    // @MappingTarget helps update the existing target
    void updateEntity(CategoryDto categoryDto, @MappingTarget Category category);
}
