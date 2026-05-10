package com.accesoriosdm.inventory.inventory.repository;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventarioMovimientoRepository
        extends JpaRepository<InventarioMovimiento, Integer>, JpaSpecificationExecutor<InventarioMovimiento> {
}
