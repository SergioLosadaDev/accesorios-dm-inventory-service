package com.accesoriosdm.inventory.inventory.dto;

import com.accesoriosdm.inventory.inventory.entity.TipoMovimiento;

public record TipoMovimientoInfo(Integer id, String nombre) {

    public static TipoMovimientoInfo from(TipoMovimiento t) {
        return new TipoMovimientoInfo(t.getId(), t.getNombre());
    }
}
