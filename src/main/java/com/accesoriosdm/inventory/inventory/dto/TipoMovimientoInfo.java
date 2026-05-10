package com.accesoriosdm.inventory.inventory.dto;

import com.accesoriosdm.inventory.inventory.entity.TipoMovimiento;

import java.util.UUID;

public record TipoMovimientoInfo(UUID id, String nombre) {

    public static TipoMovimientoInfo from(TipoMovimiento t) {
        return new TipoMovimientoInfo(t.getId(), t.getNombre());
    }
}
