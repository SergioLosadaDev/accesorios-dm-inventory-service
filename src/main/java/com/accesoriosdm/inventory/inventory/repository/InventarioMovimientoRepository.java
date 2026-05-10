package com.accesoriosdm.inventory.inventory.repository;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InventarioMovimientoRepository
        extends JpaRepository<InventarioMovimiento, UUID>, JpaSpecificationExecutor<InventarioMovimiento> {

    @Query(value = """
            SELECT COALESCE(SUM(CASE WHEN t.codigo = 'ENTRADA' THEN m.cantidad ELSE -m.cantidad END), 0)
            FROM inventario.inventario_movimiento m
            JOIN inventario.tipo_movimiento t ON t.id = m.tipo_movimiento_id
            WHERE m.producto_id = :productoId
            """, nativeQuery = true)
    int calcularStock(@Param("productoId") UUID productoId);
}
