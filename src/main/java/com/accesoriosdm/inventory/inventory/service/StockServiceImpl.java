package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import com.accesoriosdm.inventory.catalog.specification.ProductoSpecification;
import com.accesoriosdm.inventory.exception.ProductNotFoundException;
import com.accesoriosdm.inventory.inventory.dto.StockResponse;
import com.accesoriosdm.inventory.inventory.repository.InventarioMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductoRepository productoRepository;
    private final InventarioMovimientoRepository movimientoRepository;

    @Override
    @Transactional(readOnly = true)
    public StockResponse obtenerStock(UUID productoId) {
        var producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductNotFoundException(productoId.toString()));
        int stock = movimientoRepository.calcularStock(productoId);
        return new StockResponse(
                producto.getId(),
                producto.getSku(),
                producto.getNombre(),
                stock,
                producto.getActualizadoEn()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockResponse> listarStock(Pageable pageable) {
        return productoRepository
                .findAll(ProductoSpecification.withFilters(null, null, null, true), pageable)
                .map(p -> new StockResponse(
                        p.getId(),
                        p.getSku(),
                        p.getNombre(),
                        movimientoRepository.calcularStock(p.getId()),
                        p.getActualizadoEn()
                ));
    }
}
