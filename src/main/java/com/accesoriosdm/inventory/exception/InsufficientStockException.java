package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends DomainException {

    public InsufficientStockException(String productId, int requested, int available) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "INSUFFICIENT_STOCK",
                "Stock insuficiente para el producto '" + productId +
                "'. Solicitado: " + requested + ", disponible: " + available + ".");
    }
}
