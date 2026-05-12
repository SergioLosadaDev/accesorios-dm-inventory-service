package com.accesoriosdm.inventory.inventory.service;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final InventarioMovimientoRepository movimientoRepository;
    private final TipoMovimientoRepository tipoMovimientoRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public MovimientoResponse registrar(RegistrarMovimientoRequest request) {
        var producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ProductNotFoundException(request.productoId().toString()));

        var tipo = tipoMovimientoRepository.findById(request.tipoMovimientoId())
                .orElseThrow(() -> new InvalidMovementTypeException(request.tipoMovimientoId().toString()));

        int cantidadAlmacenada = request.cantidad();

        if ("SALIDA".equalsIgnoreCase(tipo.getNombre())) {
            int stockActual = producto.getStock() != null ? producto.getStock() : 0;
            if (stockActual < request.cantidad()) {
                throw new InsufficientStockException(
                        request.productoId().toString(), request.cantidad(), stockActual);
            }
            cantidadAlmacenada = -request.cantidad();
        }

        var movimiento = InventarioMovimiento.builder()
                .idProducto(request.productoId())
                .tipoMovimiento(tipo)
                .cantidad(cantidadAlmacenada)
                .referencia(request.referencia())
                .build();

        return MovimientoResponse.from(movimientoRepository.save(movimiento));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoHistorialResponse> listar(
            Integer productoId, Integer tipoMovimientoId,
            LocalDateTime fechaDesde, LocalDateTime fechaHasta, Pageable pageable) {

        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new RangoFechasInvalidoException();
        }

        Page<InventarioMovimiento> page = movimientoRepository.findAll(
                MovimientoSpecification.withFilters(productoId, tipoMovimientoId, fechaDesde, fechaHasta),
                pageable);

        List<Integer> productIds = page.getContent().stream()
                .map(InventarioMovimiento::getIdProducto)
                .distinct().toList();

        Map<Integer, Producto> productos = productoRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Producto::getId, p -> p));

        return page.map(m -> {
            Producto p = productos.get(m.getIdProducto());
            ProductoInfo info = p != null
                    ? new ProductoInfo(p.getId(), p.getNombre())
                    : new ProductoInfo(m.getIdProducto(), "Producto no encontrado");
            return MovimientoHistorialResponse.from(m, info);
        });
    }
}
