package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends DomainException {

    public CategoryNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND",
                "La categoría con ID '" + id + "' no fue encontrada.");
    }
}
