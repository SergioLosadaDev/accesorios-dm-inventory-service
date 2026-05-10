package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.inventory.dto.StockResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StockService {

    StockResponse obtenerStock(UUID productoId);

    Page<StockResponse> listarStock(Pageable pageable);
}
