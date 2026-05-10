package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class MaterialHasProductsException extends DomainException {

    public MaterialHasProductsException(UUID materialId) {
        super(HttpStatus.CONFLICT, "MATERIAL_HAS_PRODUCTS",
                "El material '" + materialId + "' tiene productos asociados y no puede eliminarse.");
    }
}
