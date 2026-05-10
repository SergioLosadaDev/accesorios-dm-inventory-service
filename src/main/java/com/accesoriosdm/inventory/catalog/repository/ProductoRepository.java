package com.accesoriosdm.inventory.catalog.repository;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductoRepository
        extends JpaRepository<Producto, UUID>, JpaSpecificationExecutor<Producto> {

    boolean existsBySku(String sku);

    boolean existsByCategoriaId(UUID categoriaId);

    boolean existsByMaterialId(UUID materialId);
}
