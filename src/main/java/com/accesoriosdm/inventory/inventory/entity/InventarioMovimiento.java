package com.accesoriosdm.inventory.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventario_movimiento", schema = "inventario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "producto_id", nullable = false)
    private UUID productoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(length = 100)
    private String referencia;

    @CreationTimestamp
    @Column(name = "registrado_en", nullable = false, updatable = false)
    private Instant registradoEn;
}
