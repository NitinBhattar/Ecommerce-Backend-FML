package com.ecommercefml.product.validation;

import com.ecommercefml.product.dto.ProductDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DiscountedPriceValidator implements ConstraintValidator<ValidDiscountedPrice, ProductDto> {
    @Override
    public boolean isValid(ProductDto productDto, ConstraintValidatorContext context) {
        if (productDto == null) {
            return true;
        }
        if (productDto.getDiscountedPrice() == null) {
            return true;
        }
        if (productDto.getPrice() == null) {
            return true;
        }

        return productDto.getDiscountedPrice().compareTo(productDto.getPrice()) <= 0;
    }
}
