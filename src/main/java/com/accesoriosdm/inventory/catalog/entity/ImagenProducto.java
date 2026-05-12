package com.accesoriosdm.inventory.catalog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagen_producto", schema = "catalogo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "url_imagen", nullable = false, columnDefinition = "TEXT")
    private String urlImagen;

    @Column(nullable = false)
    @Builder.Default
    private Integer orden = 1;
}
