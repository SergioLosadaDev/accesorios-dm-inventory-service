package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.ImagenProducto;

public record ImagenProductoResponse(
        Integer id,
        String urlImagen,
        int orden
) {
    public static ImagenProductoResponse from(ImagenProducto img) {
        return new ImagenProductoResponse(
                img.getId(),
                img.getUrlImagen(),
                img.getOrden()
        );
    }
}
