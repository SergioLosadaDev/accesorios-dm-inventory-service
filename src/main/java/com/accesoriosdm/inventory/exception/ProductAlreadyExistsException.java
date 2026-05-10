package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends DomainException {

    public ProductAlreadyExistsException(String sku) {
        super(HttpStatus.CONFLICT, "PRODUCT_ALREADY_EXISTS",
                "Ya existe un producto con el SKU '" + sku + "'.");
    }
}
