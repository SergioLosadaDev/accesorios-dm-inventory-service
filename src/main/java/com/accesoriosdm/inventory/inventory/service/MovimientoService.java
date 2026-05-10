package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.inventory.dto.MovimientoHistorialResponse;
import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface MovimientoService {

    MovimientoResponse registrar(RegistrarMovimientoRequest request, UUID responsableId);

    Page<MovimientoHistorialResponse> listar(
            UUID productoId, UUID tipoMovimientoId, UUID responsableId,
            Instant fechaDesde, Instant fechaHasta, Pageable pageable);
}
