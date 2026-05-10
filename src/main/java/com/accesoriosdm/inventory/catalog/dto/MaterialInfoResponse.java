package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Material;

import java.util.UUID;

public record MaterialInfoResponse(UUID id, String nombre) {
    public static MaterialInfoResponse from(Material m) {
        return new MaterialInfoResponse(m.getId(), m.getNombre());
    }
}
