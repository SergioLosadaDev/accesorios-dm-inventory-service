package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.CategoriaCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.CategoriaResponse;
import com.accesoriosdm.inventory.catalog.dto.CategoriaUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {

    Page<CategoriaResponse> listar(Pageable pageable);

    CategoriaResponse obtener(Integer id);

    CategoriaResponse crear(CategoriaCreateRequest request);

    CategoriaResponse actualizar(Integer id, CategoriaUpdateRequest request);

    void eliminar(Integer id);
}
