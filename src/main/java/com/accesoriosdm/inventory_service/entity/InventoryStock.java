package com.accesoriosdm.inventory_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario_stock", schema = "inventario")
public class InventoryStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_id", nullable = false, unique = true)
    private Long productId;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity = 0;  // Valor por defecto

    @Column(name = "ultima_actualizacion")
    private LocalDateTime lastUpdate;

    public InventoryStock() {
        this.quantity = 0;
        this.lastUpdate = LocalDateTime.now();
    }

    // Constructor con parámetros
    public InventoryStock(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity != null ? quantity : 0;
        this.lastUpdate = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
}