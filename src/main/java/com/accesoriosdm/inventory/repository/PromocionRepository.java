package com.accesoriosdm.inventory.repository;

import com.accesoriosdm.inventory.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    
    List<Promocion> findByActivoTrue();
    
    @Query("SELECT p FROM Promocion p WHERE p.activo = true AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    List<Promocion> findPromocionesVigentes(@Param("fecha") LocalDateTime fecha);
    
    // Esta consulta necesita JOIN con PromocionProducto
    @Query("SELECT p FROM Promocion p JOIN p.promocionProductos pp WHERE pp.producto.idProducto = :productoId AND p.activo = true AND p.fechaInicio <= :fecha AND p.fechaFin >= :fecha")
    List<Promocion> findPromocionesVigentesByProducto(@Param("productoId") Integer productoId, @Param("fecha") LocalDateTime fecha);
}