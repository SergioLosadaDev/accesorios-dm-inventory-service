package com.accesoriosdm.inventory.inventory.repository;

import com.accesoriosdm.inventory.inventory.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, UUID> {

    Optional<TipoMovimiento> findByCodigo(String codigo);
}
