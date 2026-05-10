package com.accesoriosdm.inventory.inventory.controller;

import com.accesoriosdm.inventory.exception.ForbiddenException;
import com.accesoriosdm.inventory.inventory.dto.MovimientoResponse;
import com.accesoriosdm.inventory.inventory.dto.RegistrarMovimientoRequest;
import com.accesoriosdm.inventory.inventory.service.MovimientoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory/movements")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse registrar(
            @Valid @RequestBody RegistrarMovimientoRequest request,
            HttpServletRequest httpRequest) {
        requireAdminOrVendedor(httpRequest);
        UUID responsableId = parseUserId(httpRequest);
        return movimientoService.registrar(request, responsableId);
    }

    private void requireAdminOrVendedor(HttpServletRequest request) {
        String roles = request.getHeader("X-User-Roles");
        if (roles == null || (!roles.contains("ADMIN") && !roles.contains("ROLE_VENDEDOR"))) {
            throw new ForbiddenException("Se requiere rol ADMIN o ROLE_VENDEDOR para esta operación.");
        }
    }

    private UUID parseUserId(HttpServletRequest request) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null || userId.isBlank()) return null;
        try {
            return UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
