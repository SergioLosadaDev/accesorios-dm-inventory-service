package com.accesoriosdm.inventory.repository;

import com.accesoriosdm.inventory.entity.PromocionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionProductoRepository extends JpaRepository<PromocionProducto, Integer> {
    List<PromocionProducto> findByPromocionIdPromocion(Integer promocionId);
    List<PromocionProducto> findByProductoIdProducto(Integer productoId);
    Optional<PromocionProducto> findByPromocionIdPromocionAndProductoIdProducto(Integer promocionId, Integer productoId);
}