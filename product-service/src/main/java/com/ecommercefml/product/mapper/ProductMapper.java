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

    Product toEntity(ProductDto productDto, Category category);

    Product toEntity(CreateProductDto createProductDto, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    // @MappingTarget helps update the existing target
    void updateEntity(ProductDto productDto, @MappingTarget Product product);
}
