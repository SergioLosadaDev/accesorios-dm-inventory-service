package com.accesoriosdm.inventory.inventory.controller;

import com.accesoriosdm.inventory.exception.ForbiddenException;
import com.accesoriosdm.inventory.inventory.dto.MovimientoHistorialResponse;
import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;
import com.accesoriosdm.inventory.inventory.service.MovimientoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/inventory/movements")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public Page<MovimientoHistorialResponse> listar(
            @RequestParam(required = false) Integer productoId,
            @RequestParam(required = false) Integer tipoMovimientoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta,
            @PageableDefault(size = 20, sort = "fechaMovimiento", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpRequest) {
        requireAdmin(httpRequest);
        return movimientoService.listar(productoId, tipoMovimientoId, fechaDesde, fechaHasta, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse registrar(
            @Valid @RequestBody RegistrarMovimientoRequest request,
            HttpServletRequest httpRequest) {
        requireAdminOrVendedor(httpRequest);
        return movimientoService.registrar(request);
    }

    private void requireAdmin(HttpServletRequest request) {
        String roles = request.getHeader("X-User-Roles");
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ForbiddenException("Se requiere el rol ADMIN para esta operación.");
        }
    }

    private void requireAdminOrVendedor(HttpServletRequest request) {
        String roles = request.getHeader("X-User-Roles");
        if (roles == null || (!roles.contains("ADMIN") && !roles.contains("ROLE_VENDEDOR"))) {
            throw new ForbiddenException("Se requiere rol ADMIN o ROLE_VENDEDOR para esta operación.");
        }
    }
}
