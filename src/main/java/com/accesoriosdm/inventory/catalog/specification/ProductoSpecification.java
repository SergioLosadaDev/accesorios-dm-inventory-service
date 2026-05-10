package com.accesoriosdm.inventory.catalog.specification;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ProductoSpecification {

    private ProductoSpecification() {}

    public static Specification<Producto> withFilters(
            UUID categoriaId, UUID materialId, String nombre, Boolean activo) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoriaId != null) {
                predicates.add(cb.equal(root.get("categoria").get("id"), categoriaId));
            }
            if (materialId != null) {
                predicates.add(cb.equal(root.get("material").get("id"), materialId));
            }
            if (nombre != null && !nombre.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nombre")),
                        "%" + nombre.toLowerCase() + "%"
                ));
            }
            if (activo != null) {
                predicates.add(cb.equal(root.get("activo"), activo));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
