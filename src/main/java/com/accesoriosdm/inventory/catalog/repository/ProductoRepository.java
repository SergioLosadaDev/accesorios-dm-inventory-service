package com.accesoriosdm.inventory.catalog.repository;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer>, JpaSpecificationExecutor<Producto> {

    boolean existsByCategoriaId(Integer categoriaId);

    boolean existsByMaterialId(Integer materialId);

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.imagenes WHERE p.id = :id")
    Optional<Producto> findByIdWithImagenes(@Param("id") Integer id);
}
