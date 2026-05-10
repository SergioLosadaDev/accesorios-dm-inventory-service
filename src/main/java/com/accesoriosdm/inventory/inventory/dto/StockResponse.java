package com.accesoriosdm.inventory.inventory.dto;

import java.time.Instant;
import java.util.UUID;

public record StockResponse(
        UUID productoId,
        String sku,
        String nombre,
        int cantidadDisponible,
        Instant actualizadoEn
) {}
