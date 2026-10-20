package com.ecommercefml.product.service.impl;

import com.ecommercefml.product.dto.CreateProductDto;
import com.ecommercefml.product.dto.ProductDto;
import com.ecommercefml.product.enums.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {
    ProductDto createProduct(CreateProductDto createProductDto);

    ProductDto uploadImage(Long id, MultipartFile file) throws IOException;

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

    ProductDto getProductById(Long id);

    Page<ProductDto> getAllProduct(int page, int size, String sortBy, SortDirection sortDirection);

    Page<ProductDto> searchProduct(String keyword, int page, int size);

    Page<ProductDto> filterProductByCategory(Long categoryId, int page, int size);

    Page<ProductDto> filterProductByPrice(BigDecimal minPrice, BigDecimal maxPrice, int page, int size);

    Page<ProductDto> advanceFilterProduct(String keyword, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String brand, int page, int size);
}
