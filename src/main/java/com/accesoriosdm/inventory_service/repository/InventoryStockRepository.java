package com.accesoriosdm.inventory_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesoriosdm.inventory_service.entity.InventoryStock;

public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {
    Optional<InventoryStock> findByProductId(Long productId);
}