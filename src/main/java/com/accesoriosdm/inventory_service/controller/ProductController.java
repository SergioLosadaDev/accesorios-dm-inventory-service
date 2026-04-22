package com.accesoriosdm.inventory_service.controller;

import com.accesoriosdm.inventory_service.dto.ProductCreateRequest;
import com.accesoriosdm.inventory_service.dto.ProductResponse;
import com.accesoriosdm.inventory_service.dto.ProductUpdateRequest;
import com.accesoriosdm.inventory_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ProductResponse create(@RequestBody ProductCreateRequest request) {
        return service.create(request);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // READ LIST (filtros opcionales)
    // Ej: /api/products?material=RODIO&category=COLLARES
    @GetMapping
    public List<ProductResponse> list(
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String category
    ) {
        return service.list(material, category);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        return service.update(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}