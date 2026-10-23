package com.ecommercefml.product.service.impl;

import com.ecommercefml.product.dto.CreateProductDto;
import com.ecommercefml.product.dto.ProductDto;
import com.ecommercefml.product.entity.Category;
import com.ecommercefml.product.entity.Product;
import com.ecommercefml.product.enums.SortDirection;
import com.ecommercefml.product.expection.InvalidPriceRangeException;
import com.ecommercefml.product.expection.ResourceNotFoundException;
import com.ecommercefml.product.mapper.ProductMapper;
import com.ecommercefml.product.repository.CategoryRepository;
import com.ecommercefml.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Value("${app.product.image.max-size}")
    private long maxImageSize;

    @Value("${app.product.image.allowed-types:}")
    private List<String> allowedTypes;

    @Value("${app.product.image.allowed-extensions:}")
    private List<String> allowedExtensions;

    // Path for uploading
    @Value("${app.product.image.upload-dir}")
    private String uploadDir;

    @Override
    public ProductDto createProduct(CreateProductDto createProductDto) {
        // Fetch category from repo
        Category category = categoryRepository.findById(createProductDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = productMapper.toEntity(createProductDto, category);
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto uploadImage(Long id, MultipartFile file) throws IOException {
        // Fetch from repo
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // No file
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("No image to upload");
        }

        // Size check
        if (file.getSize() > maxImageSize) {
            throw new IllegalArgumentException("Image size must not exceed " + maxImageSize + " bytes");
        }

        // MIME Check, PDF -> PNG (extension change fakes)
        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        // Type check
        String originalName = file.getOriginalFilename();

        // Doesn't contain file
        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String extension = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();

        if (!allowedExtensions.contains(extension)) {
            throw new IllegalArgumentException("Unsupported file type");
        }

        // File path
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // Creates if it doesn't exist, does nothing if it already exists
        Files.createDirectories(uploadPath);

        // Random name
        String fileName = UUID.randomUUID().toString() + "." + extension;

        // Save file
        Path filePath = Paths.get(uploadPath + fileName);
        Files.write(filePath, file.getBytes());

        // Create URL and put in DB
        product.setImageUrl(uploadDir + fileName);
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        // Fetch from repo
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Make updates
        productMapper.updateEntity(productDto, product);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        // Fetch from repo
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        // Fetch from repo
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getAllProduct(int page, int size, String sortBy, SortDirection sortDirection) {
        Sort sort = (sortDirection.equals(SortDirection.ASC) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        // Reference operator
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProduct(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);

        // Reference operator
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProductByCategory(Long categoryId, int page, int size) {
        // Checking if categoryId is null
        if (categoryId == null) {
            throw new IllegalArgumentException("Category id is missing");
        }

        // Checking if categoryId exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);

        // Reference operator
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> filterProductByPrice(BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
        // Checking for minPrice and maxPrice
        if (minPrice == null || maxPrice == null || minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(minPrice) < 0) {
            throw new InvalidPriceRangeException("Invalid range");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);

        // Reference operator
        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> advanceFilterProduct(String keyword, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String brand, int page, int size) {
        // Checking if categoryId is null
        if (categoryId == null) {
            throw new IllegalArgumentException("Category id is missing");
        }

        // Checking if categoryId exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found");
        }

        // Checking for minPrice and maxPrice
        if (minPrice == null || maxPrice == null || minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(minPrice) < 0) {
            throw new InvalidPriceRangeException("Invalid range");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.advanceFilter(keyword, categoryId, minPrice, maxPrice, brand, pageable);

        // Reference operator
        return productPage.map(productMapper::toDto);
    }
}
