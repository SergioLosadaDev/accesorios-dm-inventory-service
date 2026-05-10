package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.CategoriaCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.CategoriaResponse;
import com.accesoriosdm.inventory.catalog.dto.CategoriaUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoriaService {

    Page<CategoriaResponse> listar(Pageable pageable);

    CategoriaResponse obtener(UUID id);

    CategoriaResponse crear(CategoriaCreateRequest request);

    CategoriaResponse actualizar(UUID id, CategoriaUpdateRequest request);

    void eliminar(UUID id);
}
