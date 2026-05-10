package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class MaterialNotFoundException extends DomainException {

    public MaterialNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, "MATERIAL_NOT_FOUND",
                "El material con ID '" + id + "' no fue encontrado.");
    }
}
