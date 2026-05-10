package com.accesoriosdm.inventory.catalog.repository;

import com.accesoriosdm.inventory.catalog.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Page<Categoria> findByEstadoTrue(Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);
}
