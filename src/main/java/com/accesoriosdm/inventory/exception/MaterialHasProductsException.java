package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class MaterialHasProductsException extends DomainException {

    public MaterialHasProductsException(String materialId) {
        super(HttpStatus.CONFLICT, "MATERIAL_HAS_PRODUCTS",
                "El material '" + materialId + "' tiene productos asociados y no puede eliminarse.");
    }
}
