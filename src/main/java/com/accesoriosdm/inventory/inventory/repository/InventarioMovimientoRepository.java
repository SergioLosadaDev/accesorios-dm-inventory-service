package com.accesoriosdm.inventory.inventory.repository;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface InventarioMovimientoRepository extends JpaRepository<InventarioMovimiento, UUID> {

    @Query(value = """
            SELECT COALESCE(SUM(CASE WHEN tipo = 'ENTRADA' THEN cantidad ELSE -cantidad END), 0)
            FROM inventario.inventario_movimiento
            WHERE producto_id = :productoId
            """, nativeQuery = true)
    int calcularStock(@Param("productoId") UUID productoId);
}
