package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import com.accesoriosdm.inventory.catalog.specification.ProductoSpecification;
import com.accesoriosdm.inventory.exception.ProductNotFoundException;
import com.accesoriosdm.inventory.inventory.dto.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public StockResponse obtenerStock(Integer productoId) {
        var producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductNotFoundException(productoId.toString()));
        return new StockResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getStock() != null ? producto.getStock() : 0,
                producto.getFechaCreacion()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockResponse> listarStock(Pageable pageable) {
        return productoRepository
                .findAll(ProductoSpecification.withFilters(null, null, null, true), pageable)
                .map(p -> new StockResponse(
                        p.getId(),
                        p.getNombre(),
                        p.getStock() != null ? p.getStock() : 0,
                        p.getFechaCreacion()
                ));
    }
}
