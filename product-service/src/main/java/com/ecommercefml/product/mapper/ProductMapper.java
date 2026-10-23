package com.ecommercefml.product.mapper;

import com.ecommercefml.product.dto.CreateProductDto;
import com.ecommercefml.product.dto.ProductDto;
import com.ecommercefml.product.entity.Category;
import com.ecommercefml.product.entity.Product;
import org.mapstruct.*;

// componentModel = "spring" means making it a spring bean
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    @Mapping(source = "productDto.name", target = "name")
    @Mapping(source = "productDto.description", target = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category", target = "category")
    Product toEntity(ProductDto productDto, Category category);

    @Mapping(source = "createProductDto.name", target = "name")
    @Mapping(source = "createProductDto.description", target = "description")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category", target = "category")
    Product toEntity(CreateProductDto createProductDto, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    // @MappingTarget helps update the existing target
    void updateEntity(ProductDto productDto, @MappingTarget Product product);
}
