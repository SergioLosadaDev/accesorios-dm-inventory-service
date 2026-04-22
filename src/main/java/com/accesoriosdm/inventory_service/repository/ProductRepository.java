package com.accesoriosdm.inventory_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesoriosdm.inventory_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Como 'material' no existe en la nueva BD, eliminamos esos métodos
    // Solo mantenemos búsqueda por categoría (categoria_id)
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findByActiveTrue();
}