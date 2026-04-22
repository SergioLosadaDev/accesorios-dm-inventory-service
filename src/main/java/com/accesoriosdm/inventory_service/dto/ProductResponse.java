package com.accesoriosdm.inventory_service.dto;

import java.math.BigDecimal;

public class ProductResponse {
    public Long id;
    public String name;
    public String material;
    public String category;
    public BigDecimal price;
    public Integer stock;
    public String imageUrl;
    public String description;
}