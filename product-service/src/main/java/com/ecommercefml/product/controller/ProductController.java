package com.ecommercefml.product.controller;

import com.ecommercefml.product.dto.CreateProductDto;
import com.ecommercefml.product.dto.ProductDto;
import com.ecommercefml.product.enums.SortDirection;
import com.ecommercefml.product.service.impl.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductDto createProductDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(createProductDto));
    }

    @PutMapping("/image-upload/{id}")
    public ResponseEntity<ProductDto> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.uploadImage(id, file));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "ASC") SortDirection sortDirection) {
        return ResponseEntity.ok(productService.getAllProduct(page, size, sortBy, sortDirection));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProduct(@RequestParam String keyword,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.searchProduct(keyword, page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProductByCategory(@RequestParam Long categoryId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.filterProductByCategory(categoryId, page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> filterProductByPrice(@RequestParam BigDecimal minValue,
                                                                 @RequestParam BigDecimal maxValue,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.filterProductByPrice(minValue, maxValue, page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDto>> advanceFilterProduct(@RequestParam(required = false) String keyword,
                                                                 @RequestParam(required = false) Long categoryId,
                                                                 @RequestParam(required = false) BigDecimal minValue,
                                                                 @RequestParam(required = false) BigDecimal maxValue,
                                                                 @RequestParam(required = false) String Brand,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.advanceFilterProduct(keyword, categoryId, minValue, maxValue, Brand, page, size));
    }
}
