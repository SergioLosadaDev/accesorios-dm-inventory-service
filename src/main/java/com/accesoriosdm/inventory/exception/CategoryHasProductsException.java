package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class CategoryHasProductsException extends DomainException {

    public CategoryHasProductsException(String categoryId) {
        super(HttpStatus.CONFLICT, "CATEGORY_HAS_PRODUCTS",
                "La categoría '" + categoryId + "' tiene productos asociados y no puede eliminarse.");
    }
}
