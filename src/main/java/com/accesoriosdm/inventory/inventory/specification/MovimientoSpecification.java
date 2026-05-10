package com.accesoriosdm.inventory.inventory.specification;

import com.accesoriosdm.inventory.inventory.entity.InventarioMovimiento;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class MovimientoSpecification {

    private MovimientoSpecification() {}

    public static Specification<InventarioMovimiento> withFilters(
            Integer productoId, Integer tipoMovimientoId,
            LocalDateTime fechaDesde, LocalDateTime fechaHasta) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productoId != null) {
                predicates.add(cb.equal(root.get("idProducto"), productoId));
            }
            if (tipoMovimientoId != null) {
                predicates.add(cb.equal(root.get("tipoMovimiento").get("id"), tipoMovimientoId));
            }
            if (fechaDesde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fechaMovimiento"), fechaDesde));
            }
            if (fechaHasta != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fechaMovimiento"), fechaHasta));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
