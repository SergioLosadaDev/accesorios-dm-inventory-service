package com.accesoriosdm.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesoriosdm.inventory.dto.PromocionDTO;
import com.accesoriosdm.inventory.service.PromocionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/promociones")
@RequiredArgsConstructor
@Slf4j
public class PromocionController {

    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<PromocionDTO>> getAllPromociones() {
        log.info("GET /promociones - Obteniendo todas las promociones");
        return ResponseEntity.ok(promocionService.getAllPromociones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromocionDTO> getPromocionById(@PathVariable Integer id) {
        log.info("GET /promociones/{} - Obteniendo promoción por ID", id);
        return ResponseEntity.ok(promocionService.getPromocionById(id));
    }

    @PostMapping
    public ResponseEntity<PromocionDTO> createPromocion(@RequestBody PromocionDTO dto) {
        log.info("POST /promociones - Creando nueva promoción: {}", dto.getNombre());
        PromocionDTO created = promocionService.createPromocion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionDTO> updatePromocion(@PathVariable Integer id, @RequestBody PromocionDTO dto) {
        log.info("PUT /promociones/{} - Actualizando promoción", id);
        PromocionDTO updated = promocionService.updatePromocion(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocion(@PathVariable Integer id) {
        log.info("DELETE /promociones/{} - Eliminando promoción", id);
        promocionService.deletePromocion(id);
        return ResponseEntity.noContent().build();
    }
}