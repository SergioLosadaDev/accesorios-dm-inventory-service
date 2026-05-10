package com.accesoriosdm.inventory.catalog.controller;

import com.accesoriosdm.inventory.catalog.dto.CategoriaCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.CategoriaResponse;
import com.accesoriosdm.inventory.catalog.dto.CategoriaUpdateRequest;
import com.accesoriosdm.inventory.catalog.service.CategoriaService;
import com.accesoriosdm.inventory.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog/categories")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public Page<CategoriaResponse> listar(
            @PageableDefault(size = 20) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public CategoriaResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse crear(
            @Valid @RequestBody CategoriaCreateRequest request,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public CategoriaResponse actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaUpdateRequest request,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @PathVariable Integer id,
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
