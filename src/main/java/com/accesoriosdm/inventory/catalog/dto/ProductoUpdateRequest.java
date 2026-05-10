package com.accesoriosdm.inventory.catalog.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoUpdateRequest(

        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
        String descripcion,

        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        Integer categoriaId,

        Integer materialId,

        Boolean estado
) {}
