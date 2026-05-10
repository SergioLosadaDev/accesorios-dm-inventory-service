package com.accesoriosdm.inventory.inventory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record RegistrarMovimientoRequest(

        @NotNull(message = "El productoId es obligatorio.")
        UUID productoId,

        @NotNull(message = "El tipoMovimientoId es obligatorio.")
        UUID tipoMovimientoId,

        @NotNull(message = "La cantidad es obligatoria.")
        @Positive(message = "La cantidad debe ser un número entero positivo.")
        Integer cantidad,

        @NotNull(message = "El motivo es obligatorio.")
        String motivo
) {}
