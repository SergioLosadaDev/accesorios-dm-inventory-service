package com.accesoriosdm.inventory.catalog.controller;

import com.accesoriosdm.inventory.catalog.dto.ProductoCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.ProductoDetailResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoUpdateRequest;
import com.accesoriosdm.inventory.catalog.service.ProductoService;
import com.accesoriosdm.inventory.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/catalog/products")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public Page<ProductoResponse> listar(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID materialId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Boolean activo,
            @PageableDefault(size = 20) Pageable pageable) {
        return service.listar(categoryId, materialId, nombre, activo, pageable);
    }

    @GetMapping("/{id}")
    public ProductoDetailResponse obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDetailResponse crear(
            @Valid @RequestBody ProductoCreateRequest request,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public ProductoDetailResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoUpdateRequest request,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        service.eliminar(id);
    }

    private void requireAdmin(HttpServletRequest request) {
        String roles = request.getHeader("X-User-Roles");
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ForbiddenException("Se requiere el rol ADMIN para esta operación.");
        }
    }
}
