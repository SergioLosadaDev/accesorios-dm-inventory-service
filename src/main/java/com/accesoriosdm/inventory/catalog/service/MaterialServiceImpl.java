package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.MaterialCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.MaterialResponse;
import com.accesoriosdm.inventory.catalog.dto.MaterialUpdateRequest;
import com.accesoriosdm.inventory.catalog.entity.Material;
import com.accesoriosdm.inventory.catalog.repository.MaterialRepository;
import com.accesoriosdm.inventory.exception.MaterialNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialResponse> listar(Pageable pageable) {
        return repository.findByActivoTrue(pageable).map(MaterialResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponse obtener(UUID id) {
        return MaterialResponse.from(findOrThrow(id));
    }

    @Override
    @Transactional
    public MaterialResponse crear(MaterialCreateRequest request) {
        Material material = Material.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .activo(true)
                .build();
        return MaterialResponse.from(repository.save(material));
    }

    @Override
    @Transactional
    public MaterialResponse actualizar(UUID id, MaterialUpdateRequest request) {
        Material material = findOrThrow(id);

        if (request.nombre() != null) material.setNombre(request.nombre());
        if (request.descripcion() != null) material.setDescripcion(request.descripcion());
        if (request.activo() != null) material.setActivo(request.activo());

        return MaterialResponse.from(repository.save(material));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        Material material = findOrThrow(id);
        // TODO: check for associated products once Product entity is available (HU-DEV-SALB_14)
        // if (productRepository.existsByMaterialId(id)) throw new MaterialHasProductsException(id);
        repository.delete(material);
    }

    private Material findOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id.toString()));
    }
}
