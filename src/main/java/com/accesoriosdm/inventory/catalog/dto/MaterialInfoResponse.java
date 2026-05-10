package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Material;

public record MaterialInfoResponse(Integer id, String nombre) {
    public static MaterialInfoResponse from(Material m) {
        return new MaterialInfoResponse(m.getId(), m.getNombre());
    }
}
