package com.accesoriosdm.inventory.inventory.dto;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;

import java.time.LocalDateTime;

public record MovimientoHistorialResponse(
        Integer id,
        ProductoInfo producto,
        TipoMovimientoInfo tipoMovimiento,
        int cantidad,
        String referencia,
        LocalDateTime fechaMovimiento
) {
    public static MovimientoHistorialResponse from(InventarioMovimiento m, ProductoInfo productoInfo) {
        return new MovimientoHistorialResponse(
                m.getId(),
                productoInfo,
                TipoMovimientoInfo.from(m.getTipoMovimiento()),
                m.getCantidad(),
                m.getReferencia(),
                m.getFechaMovimiento()
        );
    }
}
