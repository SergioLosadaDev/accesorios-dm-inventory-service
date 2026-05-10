package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.inventory.dto.MovimientoHistorialResponse;
import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MovimientoService {

    MovimientoResponse registrar(RegistrarMovimientoRequest request);

    Page<MovimientoHistorialResponse> listar(
            Integer productoId, Integer tipoMovimientoId,
            LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable);
}
