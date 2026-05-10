package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Producto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProductoDetailResponse(
        UUID id,
        String sku,
        String nombre,
        String descripcion,
        BigDecimal precio,
        boolean activo,
        CategoriaInfoResponse categoria,
        MaterialInfoResponse material,
        List<ImagenProductoResponse> imagenes,
        Instant creadoEn,
        Instant actualizadoEn
) {
    public static ProductoDetailResponse from(Producto p) {
        return new ProductoDetailResponse(
                p.getId(),
                p.getSku(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getActivo(),
                CategoriaInfoResponse.from(p.getCategoria()),
                p.getMaterial() != null ? MaterialInfoResponse.from(p.getMaterial()) : null,
                p.getImagenes().stream().map(ImagenProductoResponse::from).toList(),
                p.getCreadoEn(),
                p.getActualizadoEn()
        );
    }
}
