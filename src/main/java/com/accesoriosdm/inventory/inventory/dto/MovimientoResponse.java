package com.accesoriosdm.inventory.inventory.dto;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;

import java.time.LocalDateTime;

public record MovimientoResponse(
        Integer id,
        Integer productoId,
        TipoMovimientoInfo tipoMovimiento,
        int cantidad,
        String referencia,
        LocalDateTime fechaMovimiento
) {
    public static MovimientoResponse from(InventarioMovimiento m) {
        return new MovimientoResponse(
                m.getId(),
                m.getIdProducto(),
                TipoMovimientoInfo.from(m.getTipoMovimiento()),
                m.getCantidad(),
                m.getReferencia(),
                m.getFechaMovimiento()
        );
    }
}
