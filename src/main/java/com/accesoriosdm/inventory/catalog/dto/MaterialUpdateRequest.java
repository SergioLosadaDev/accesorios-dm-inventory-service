package com.accesoriosdm.inventory.catalog.dto;

import jakarta.validation.constraints.Size;

public record MaterialUpdateRequest(

        @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
        String nombre,

        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion
) {}
