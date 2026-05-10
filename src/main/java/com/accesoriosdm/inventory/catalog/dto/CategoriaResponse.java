package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Categoria;

import java.time.Instant;
import java.util.UUID;

public record CategoriaResponse(
        UUID id,
        String nombre,
        String descripcion,
        boolean activo,
        Instant creadoEn,
        Instant actualizadoEn
) {
    public static CategoriaResponse from(Categoria c) {
        return new CategoriaResponse(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getActivo(),
                c.getCreadoEn(),
                c.getActualizadoEn()
        );
    }
}
