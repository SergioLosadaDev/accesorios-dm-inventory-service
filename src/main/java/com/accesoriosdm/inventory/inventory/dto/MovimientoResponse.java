package com.accesoriosdm.inventory.inventory.dto;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;

import java.time.Instant;
import java.util.UUID;

public record MovimientoResponse(
        UUID id,
        UUID productoId,
        TipoMovimientoInfo tipoMovimiento,
        int cantidad,
        String motivo,
        UUID responsableId,
        Instant registradoEn
) {
    public static MovimientoResponse from(InventarioMovimiento m) {
        return new MovimientoResponse(
                m.getId(),
                m.getProductoId(),
                TipoMovimientoInfo.from(m.getTipoMovimiento()),
                m.getCantidad(),
                m.getMotivo(),
                m.getResponsableId(),
                m.getRegistradoEn()
        );
    }
}
