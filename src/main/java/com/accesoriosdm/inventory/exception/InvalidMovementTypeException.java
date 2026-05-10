package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class InvalidMovementTypeException extends DomainException {

    public InvalidMovementTypeException(String type) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "INVALID_MOVEMENT_TYPE",
                "El tipo de movimiento '" + type + "' no es válido.");
    }
}
