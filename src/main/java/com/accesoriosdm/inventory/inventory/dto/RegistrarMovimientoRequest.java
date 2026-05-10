package com.accesoriosdm.inventory.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegistrarMovimientoRequest(

        @NotNull(message = "El productoId es obligatorio.")
        Integer productoId,

        @NotNull(message = "El tipoMovimientoId es obligatorio.")
        Integer tipoMovimientoId,

        @NotNull(message = "La cantidad es obligatoria.")
        @Positive(message = "La cantidad debe ser un número entero positivo.")
        Integer cantidad,

        @Size(max = 100, message = "La referencia no puede superar los 100 caracteres.")
        String referencia
) {}
