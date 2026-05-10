package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND",
                "El producto con ID '" + id + "' no fue encontrado.");
    }
}
