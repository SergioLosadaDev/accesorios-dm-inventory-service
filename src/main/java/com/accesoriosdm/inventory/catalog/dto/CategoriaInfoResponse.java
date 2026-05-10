package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Categoria;

import java.util.UUID;

public record CategoriaInfoResponse(UUID id, String nombre) {
    public static CategoriaInfoResponse from(Categoria c) {
        return new CategoriaInfoResponse(c.getId(), c.getNombre());
    }
}
