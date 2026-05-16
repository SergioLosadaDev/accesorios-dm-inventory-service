package com.accesoriosdm.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesoriosdm.inventory.entity.PromocionProducto;

@Repository
public interface PromocionProductoRepository extends JpaRepository<PromocionProducto, Integer> {
}