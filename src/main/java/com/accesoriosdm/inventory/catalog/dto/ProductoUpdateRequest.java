package com.accesoriosdm.inventory.catalog.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductoUpdateRequest(

        @Size(max = 200, message = "El nombre no puede superar los 200 caracteres")
        String nombre,

        @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
        String descripcion,

        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        UUID categoriaId,

        UUID materialId,

        Boolean activo
) {}
