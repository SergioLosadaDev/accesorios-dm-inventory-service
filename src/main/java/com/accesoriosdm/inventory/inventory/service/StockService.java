package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.inventory.dto.StockResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {

    StockResponse obtenerStock(Integer productoId);

    Page<StockResponse> listarStock(Pageable pageable);
}
