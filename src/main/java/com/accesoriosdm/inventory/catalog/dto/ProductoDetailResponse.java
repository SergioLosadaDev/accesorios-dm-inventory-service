package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.Producto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductoDetailResponse(
        Integer id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        boolean estado,
        CategoriaInfoResponse categoria,
        MaterialInfoResponse material,
        List<ImagenProductoResponse> imagenes,
        LocalDateTime fechaCreacion
) {
    public static ProductoDetailResponse from(Producto p) {
        return new ProductoDetailResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getEstado(),
                CategoriaInfoResponse.from(p.getCategoria()),
                MaterialInfoResponse.from(p.getMaterial()),
                p.getImagenes().stream().map(ImagenProductoResponse::from).toList(),
                p.getFechaCreacion()
        );
    }
}
