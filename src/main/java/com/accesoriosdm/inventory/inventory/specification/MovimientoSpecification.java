package com.accesoriosdm.inventory.inventory.specification;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MovimientoSpecification {

    private MovimientoSpecification() {}

    public static Specification<InventarioMovimiento> withFilters(
            UUID productoId, UUID tipoMovimientoId, UUID responsableId,
            Instant fechaDesde, Instant fechaHasta) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productoId != null) {
                predicates.add(cb.equal(root.get("productoId"), productoId));
            }
            if (tipoMovimientoId != null) {
                predicates.add(cb.equal(root.get("tipoMovimiento").get("id"), tipoMovimientoId));
            }
            if (responsableId != null) {
                predicates.add(cb.equal(root.get("responsableId"), responsableId));
            }
            if (fechaDesde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("registradoEn"), fechaDesde));
            }
            if (fechaHasta != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("registradoEn"), fechaHasta));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
