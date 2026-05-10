package com.accesoriosdm.inventory.catalog.specification;

import com.accesoriosdm.inventory.catalog.entity.Producto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ProductoSpecification {

    private ProductoSpecification() {}

    public static Specification<Producto> withFilters(
            Integer categoriaId, Integer materialId, String nombre, Boolean estado) {

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
            if (estado != null) {
                predicates.add(cb.equal(root.get("estado"), estado));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
