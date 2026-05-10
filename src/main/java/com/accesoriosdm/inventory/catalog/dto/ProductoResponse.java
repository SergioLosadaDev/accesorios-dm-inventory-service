package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponse(
        Integer id,
        String nombre,
        BigDecimal precio,
        Integer stock,
        boolean estado,
        CategoriaInfoResponse categoria,
        MaterialInfoResponse material,
        LocalDateTime fechaCreacion
) {
    public static ProductoResponse from(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getStock(),
                p.getEstado(),
                CategoriaInfoResponse.from(p.getCategoria()),
                MaterialInfoResponse.from(p.getMaterial()),
                p.getFechaCreacion()
        );
    }
}
