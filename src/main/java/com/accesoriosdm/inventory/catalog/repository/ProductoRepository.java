package com.accesoriosdm.inventory.catalog.repository;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository
        extends JpaRepository<Producto, UUID>, JpaSpecificationExecutor<Producto> {

    boolean existsBySku(String sku);

    boolean existsByCategoriaId(UUID categoriaId);

    boolean existsByMaterialId(UUID materialId);

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.imagenes WHERE p.id = :id")
    Optional<Producto> findByIdWithImagenes(@Param("id") UUID id);
}
