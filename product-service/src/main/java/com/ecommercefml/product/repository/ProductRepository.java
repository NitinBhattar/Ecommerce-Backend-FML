package com.ecommercefml.product.repository;

import com.ecommercefml.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // List<Product> isn't used because the list can be vv long, and it can slow down our website
    // Page solve this by making data fetch in paged format
    // Page class the info for current, prev and next page, page number, total pages, rows in one page, all the data, etc
    // Pageable has page number, page size and sorting order -> LIMIT and OFFSET in SQL
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // BETWEEN in SQL
    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    // REGEX or % or _ or LIKE
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // JPQL
    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (p.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (:brand IS NULL OR LOWER(p.brand) = LOWER(:brand))")
    Page<Product> advanceFilter(
            @Param("keyword") String keyword,
            @Param ("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("brand") String brand,
            Pageable pageable
    );


}

