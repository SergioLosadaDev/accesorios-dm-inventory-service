package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import com.accesoriosdm.inventory.exception.InsufficientStockException;
import com.accesoriosdm.inventory.exception.InvalidMovementTypeException;
import com.accesoriosdm.inventory.exception.ProductNotFoundException;
import com.accesoriosdm.inventory.exception.RangoFechasInvalidoException;
import com.accesoriosdm.inventory.inventory.dto.MovimientoHistorialResponse;
import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.ProductoInfo;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;
import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import com.accesoriosdm.inventory.inventory.repository.InventarioMovimientoRepository;
import com.accesoriosdm.inventory.inventory.repository.TipoMovimientoRepository;
import com.accesoriosdm.inventory.inventory.specification.MovimientoSpecification;
import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoHistorialResponse> listar(
            UUID productoId, UUID tipoMovimientoId, UUID responsableId,
            Instant fechaDesde, Instant fechaHasta, Pageable pageable) {

        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new RangoFechasInvalidoException();
        }

        Page<InventarioMovimiento> page = movimientoRepository.findAll(
                MovimientoSpecification.withFilters(
                        productoId, tipoMovimientoId, responsableId, fechaDesde, fechaHasta),
                pageable);

        List<UUID> productIds = page.getContent().stream()
                .map(InventarioMovimiento::getProductoId)
                .distinct().toList();

        Map<UUID, Producto> productos = productoRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Producto::getId, p -> p));

        return page.map(m -> {
            Producto p = productos.get(m.getProductoId());
            ProductoInfo info = p != null
                    ? new ProductoInfo(p.getId(), p.getSku(), p.getNombre())
                    : new ProductoInfo(m.getProductoId(), "N/A", "Producto no encontrado");
            return MovimientoHistorialResponse.from(m, info);
        });
    }
}
