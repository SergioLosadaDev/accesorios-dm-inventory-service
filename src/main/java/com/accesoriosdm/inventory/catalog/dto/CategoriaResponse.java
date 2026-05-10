package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Categoria;

public record CategoriaResponse(
        Integer id,
        String nombre,
        String descripcion,
        boolean estado
) {
    public static CategoriaResponse from(Categoria c) {
        return new CategoriaResponse(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getEstado()
        );
    }
}
