package com.accesoriosdm.inventory.catalog.repository;

import com.accesoriosdm.inventory.catalog.entity.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Integer> {

    List<ImagenProducto> findByProductoIdOrderByOrdenAsc(Integer productoId);
}
