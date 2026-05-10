package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.exception.InsufficientStockException;
import com.accesoriosdm.inventory.exception.InvalidMovementTypeException;
import com.accesoriosdm.inventory.exception.ProductNotFoundException;
import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;
import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import com.accesoriosdm.inventory.inventory.repository.InventarioMovimientoRepository;
import com.accesoriosdm.inventory.inventory.repository.TipoMovimientoRepository;
import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final InventarioMovimientoRepository movimientoRepository;
    private final TipoMovimientoRepository tipoMovimientoRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public MovimientoResponse registrar(RegistrarMovimientoRequest request, UUID responsableId) {
        if (!productoRepository.existsById(request.productoId())) {
            throw new ProductNotFoundException(request.productoId().toString());
        }

        var tipo = tipoMovimientoRepository.findById(request.tipoMovimientoId())
                .orElseThrow(() -> new InvalidMovementTypeException(request.tipoMovimientoId().toString()));

        if ("SALIDA".equals(tipo.getCodigo())) {
            int stockActual = movimientoRepository.calcularStock(request.productoId());
            if (stockActual < request.cantidad()) {
                throw new InsufficientStockException(
                        request.productoId().toString(), request.cantidad(), stockActual);
            }
        }

        var movimiento = InventarioMovimiento.builder()
                .productoId(request.productoId())
                .tipoMovimiento(tipo)
                .cantidad(request.cantidad())
                .motivo(request.motivo())
                .responsableId(responsableId)
                .build();

        return MovimientoResponse.from(movimientoRepository.save(movimiento));
    }
}
