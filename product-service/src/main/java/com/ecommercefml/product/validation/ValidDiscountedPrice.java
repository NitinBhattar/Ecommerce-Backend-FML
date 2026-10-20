package com.ecommercefml.product.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// With ElementType.TYPE, it can be placed on class/interfaces.enums etc.
// With  ElementType.FIELD, it can be placed on variables inside the class
// ElementType.TYPE
// ElementType.FIELD
// ElementType.METHOD
// ElementType.PARAMETER
// ElementType.CONSTRUCTOR
// We need access to entire class, so we do this
@Target(ElementType.TYPE)

// How long should this annotation's metadata be retained?
// It has 3 types SOURCE (During compilations), CLASS (For particular class), RUNTIME (while application is running)
// These are checked while application is running
@Retention(RetentionPolicy.RUNTIME)

// All the valid constraints
@Constraint(validatedBy = DiscountedPriceValidator.class)

// Tells java to document it
@Documented
public @interface ValidDiscountedPrice {
    String message() default "Discounted price must be less than or equal to price";

    // Different class can have different rules e.g.: @NotNull(groups = CreateProduct.class)
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
