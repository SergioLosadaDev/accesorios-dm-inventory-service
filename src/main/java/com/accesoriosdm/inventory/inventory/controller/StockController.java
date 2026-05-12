package com.accesoriosdm.inventory.inventory.controller;

import com.accesoriosdm.inventory.inventory.dto.StockResponse;
import com.accesoriosdm.inventory.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stock/{productId}")
    public StockResponse obtenerStock(@PathVariable Integer productId) {
        return stockService.obtenerStock(productId);
    }

    @GetMapping("/stock")
    public Page<StockResponse> listarStock(@PageableDefault(size = 20) Pageable pageable) {
        return stockService.listarStock(pageable);
    }
}
