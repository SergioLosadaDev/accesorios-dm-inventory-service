package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;

import java.util.UUID;

public interface MovimientoService {

    MovimientoResponse registrar(RegistrarMovimientoRequest request, UUID responsableId);
}
