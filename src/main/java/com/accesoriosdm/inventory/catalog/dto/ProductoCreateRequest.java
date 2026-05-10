package com.accesoriosdm.inventory.catalog.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductoCreateRequest(

        @NotBlank(message = "El SKU es requerido")
        @Size(max = 50, message = "El SKU no puede superar los 50 caracteres")
        String sku,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 200, message = "El nombre no puede superar los 200 caracteres")
        String nombre,

        @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
        String descripcion,

        @NotNull(message = "El precio es requerido")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "La categoría es requerida")
        UUID categoriaId,

        UUID materialId
) {}
