package com.accesoriosdm.inventory.catalog.dto;

import jakarta.validation.constraints.Size;

public record CategoriaUpdateRequest(

        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        Boolean activo
) {}
