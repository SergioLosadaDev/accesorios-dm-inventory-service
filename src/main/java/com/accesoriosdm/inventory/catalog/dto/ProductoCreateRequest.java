package com.accesoriosdm.inventory.catalog.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoCreateRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String nombre,

        @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
        String descripcion,

        @NotNull(message = "El precio es requerido")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La categoría es requerida")
        Integer categoriaId,

        @NotNull(message = "El material es requerido")
        Integer materialId
) {}
