package com.accesoriosdm.inventory.catalog.dto;

import com.accesoriosdm.inventory.catalog.entity.ImagenProducto;

import java.util.UUID;

public record ImagenProductoResponse(
        UUID id,
        String url,
        boolean esPrincipal,
        int orden
) {
    public static ImagenProductoResponse from(ImagenProducto img) {
        return new ImagenProductoResponse(
                img.getId(),
                img.getUrl(),
                img.getEsPrincipal(),
                img.getOrden()
        );
    }
}
