package com.accesoriosdm.inventory.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_movimiento", schema = "inventario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_movimiento")
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
