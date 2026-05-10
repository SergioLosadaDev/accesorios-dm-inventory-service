package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.MaterialCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.MaterialResponse;
import com.accesoriosdm.inventory.catalog.dto.MaterialUpdateRequest;
import com.accesoriosdm.inventory.catalog.entity.Material;
import com.accesoriosdm.inventory.catalog.repository.MaterialRepository;
import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import com.accesoriosdm.inventory.exception.MaterialHasProductsException;
import com.accesoriosdm.inventory.exception.MaterialNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository repository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(MaterialResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponse obtener(Integer id) {
        return MaterialResponse.from(findOrThrow(id));
    }

    @Override
    @Transactional
    public MaterialResponse crear(MaterialCreateRequest request) {
        Material material = Material.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .build();
        return MaterialResponse.from(repository.save(material));
    }

    @Override
    @Transactional
    public MaterialResponse actualizar(Integer id, MaterialUpdateRequest request) {
        Material material = findOrThrow(id);

        if (request.nombre() != null) material.setNombre(request.nombre());
        if (request.descripcion() != null) material.setDescripcion(request.descripcion());

        return MaterialResponse.from(repository.save(material));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Material material = findOrThrow(id);
        if (productoRepository.existsByMaterialId(id)) {
            throw new MaterialHasProductsException(id.toString());
        }
        repository.delete(material);
    }

    private Material findOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(id.toString()));
    }
}
