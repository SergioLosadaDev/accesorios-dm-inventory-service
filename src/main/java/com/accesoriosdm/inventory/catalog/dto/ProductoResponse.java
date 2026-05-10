package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Producto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductoResponse(
        UUID id,
        String sku,
        String nombre,
        BigDecimal precio,
        boolean activo,
        CategoriaInfoResponse categoria,
        MaterialInfoResponse material,
        Instant creadoEn
) {
    public static ProductoResponse from(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getSku(),
                p.getNombre(),
                p.getPrecio(),
                p.getActivo(),
                CategoriaInfoResponse.from(p.getCategoria()),
                p.getMaterial() != null ? MaterialInfoResponse.from(p.getMaterial()) : null,
                p.getCreadoEn()
        );
    }
}
