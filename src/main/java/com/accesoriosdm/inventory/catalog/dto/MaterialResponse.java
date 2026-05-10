package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Material;

import java.time.Instant;
import java.util.UUID;

public record MaterialResponse(
        UUID id,
        String nombre,
        String descripcion,
        boolean activo,
        Instant creadoEn,
        Instant actualizadoEn
) {
    public static MaterialResponse from(Material m) {
        return new MaterialResponse(
                m.getId(),
                m.getNombre(),
                m.getDescripcion(),
                m.getActivo(),
                m.getCreadoEn(),
                m.getActualizadoEn()
        );
    }
}
