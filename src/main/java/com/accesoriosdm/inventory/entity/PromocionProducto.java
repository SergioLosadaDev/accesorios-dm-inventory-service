package com.accesoriosdm.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "promocion_producto", schema = "promociones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion_producto")
    private Integer idPromocionProducto;

    @Column(name = "precio_promocional", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPromocional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_promocion", nullable = false)
    private Promocion promocion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
}