package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Material;

public record MaterialResponse(
        Integer id,
        String nombre,
        String descripcion
) {
    public static MaterialResponse from(Material m) {
        return new MaterialResponse(
                m.getId(),
                m.getNombre(),
                m.getDescripcion()
        );
    }
}
