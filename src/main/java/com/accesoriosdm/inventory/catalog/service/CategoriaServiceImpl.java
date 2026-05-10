package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.CategoriaCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.CategoriaResponse;
import com.accesoriosdm.inventory.catalog.dto.CategoriaUpdateRequest;
import com.accesoriosdm.inventory.catalog.entity.Categoria;
import com.accesoriosdm.inventory.catalog.repository.CategoriaRepository;
import com.accesoriosdm.inventory.exception.CategoryHasProductsException;
import com.accesoriosdm.inventory.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaResponse> listar(Pageable pageable) {
        return repository.findByActivoTrue(pageable).map(CategoriaResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse obtener(UUID id) {
        return CategoriaResponse.from(findOrThrow(id));
    }

    @Override
    @Transactional
    public CategoriaResponse crear(CategoriaCreateRequest request) {
        Categoria categoria = Categoria.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .activo(true)
                .build();
        return CategoriaResponse.from(repository.save(categoria));
    }

    @Override
    @Transactional
    public CategoriaResponse actualizar(UUID id, CategoriaUpdateRequest request) {
        Categoria categoria = findOrThrow(id);

        if (request.nombre() != null) categoria.setNombre(request.nombre());
        if (request.descripcion() != null) categoria.setDescripcion(request.descripcion());
        if (request.activo() != null) categoria.setActivo(request.activo());

        return CategoriaResponse.from(repository.save(categoria));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        Categoria categoria = findOrThrow(id);
        // TODO: check for associated products once Product entity is available (HU-DEV-SALB_13)
        // if (productRepository.existsByCategoriaId(id)) throw new CategoryHasProductsException(id);
        repository.delete(categoria);
    }

    private Categoria findOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id.toString()));
    }
}
