package com.accesoriosdm.inventory.catalog.repository;

import com.accesoriosdm.inventory.catalog.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

    boolean existsByNombreIgnoreCase(String nombre);
}
