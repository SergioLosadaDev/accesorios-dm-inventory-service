package com.accesoriosdm.inventory.inventory.dto;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;

import java.time.Instant;
import java.util.UUID;

public record MovimientoHistorialResponse(
        UUID id,
        ProductoInfo producto,
        TipoMovimientoInfo tipoMovimiento,
        int cantidad,
        String motivo,
        UUID responsableId,
        Instant registradoEn
) {
    public static MovimientoHistorialResponse from(InventarioMovimiento m, ProductoInfo productoInfo) {
        return new MovimientoHistorialResponse(
                m.getId(),
                productoInfo,
                TipoMovimientoInfo.from(m.getTipoMovimiento()),
                m.getCantidad(),
                m.getMotivo(),
                m.getResponsableId(),
                m.getRegistradoEn()
        );
    }
}
