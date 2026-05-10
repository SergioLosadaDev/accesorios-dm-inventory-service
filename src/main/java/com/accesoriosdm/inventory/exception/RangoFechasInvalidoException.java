package com.accesoriosdm.inventory.exception;

import org.springframework.http.HttpStatus;

public class RangoFechasInvalidoException extends DomainException {

    public RangoFechasInvalidoException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "VALIDATION_ERROR",
                "La fecha de inicio no puede ser posterior a la fecha de fin.");
    }
}
