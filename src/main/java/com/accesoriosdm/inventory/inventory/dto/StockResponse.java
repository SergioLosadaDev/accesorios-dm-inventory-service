package com.accesoriosdm.inventory.inventory.dto;

import java.time.LocalDateTime;

public record StockResponse(
        Integer productoId,
        String nombre,
        int cantidadDisponible,
        LocalDateTime fechaCreacion
) {}
