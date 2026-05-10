package com.accesoriosdm.inventory.catalog.controller;

import com.accesoriosdm.inventory.catalog.dto.MaterialCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.MaterialResponse;
import com.accesoriosdm.inventory.catalog.dto.MaterialUpdateRequest;
import com.accesoriosdm.inventory.catalog.service.MaterialService;
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
@RequestMapping("/api/v1/catalog/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService service;

    @GetMapping
    public Page<MaterialResponse> listar(
            @PageableDefault(size = 20) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public MaterialResponse obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponse crear(
            @Valid @RequestBody MaterialCreateRequest request,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public MaterialResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody MaterialUpdateRequest request,
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
