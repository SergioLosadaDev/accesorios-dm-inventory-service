package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.MaterialCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.MaterialResponse;
import com.accesoriosdm.inventory.catalog.dto.MaterialUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MaterialService {

    Page<MaterialResponse> listar(Pageable pageable);

    MaterialResponse obtener(UUID id);

    MaterialResponse crear(MaterialCreateRequest request);

    MaterialResponse actualizar(UUID id, MaterialUpdateRequest request);

    void eliminar(UUID id);
}
