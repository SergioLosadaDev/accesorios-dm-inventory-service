package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.ProductoCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.ProductoDetailResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoUpdateRequest;
import com.accesoriosdm.inventory.catalog.entity.Producto;
import com.accesoriosdm.inventory.catalog.repository.CategoriaRepository;
import com.accesoriosdm.inventory.catalog.repository.MaterialRepository;
import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import com.accesoriosdm.inventory.catalog.specification.ProductoSpecification;
import com.accesoriosdm.inventory.exception.CategoryNotFoundException;
import com.accesoriosdm.inventory.exception.MaterialNotFoundException;
import com.accesoriosdm.inventory.exception.ProductAlreadyExistsException;
import com.accesoriosdm.inventory.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MaterialRepository materialRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponse> listar(UUID categoriaId, UUID materialId,
                                         String nombre, Boolean activo, Pageable pageable) {
        return productoRepository
                .findAll(ProductoSpecification.withFilters(categoriaId, materialId, nombre, activo), pageable)
                .map(ProductoResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDetailResponse obtener(UUID id) {
        return ProductoDetailResponse.from(findOrThrow(id));
    }

    @Override
    @Transactional
    public ProductoDetailResponse crear(ProductoCreateRequest request) {
        if (productoRepository.existsBySku(request.sku())) {
            throw new ProductAlreadyExistsException(request.sku());
        }

        var categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoriaId().toString()));

        var material = request.materialId() != null
                ? materialRepository.findById(request.materialId())
                        .orElseThrow(() -> new MaterialNotFoundException(request.materialId().toString()))
                : null;

        var producto = Producto.builder()
                .sku(request.sku())
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .precio(request.precio())
                .categoria(categoria)
                .material(material)
                .activo(true)
                .build();

        return ProductoDetailResponse.from(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public ProductoDetailResponse actualizar(UUID id, ProductoUpdateRequest request) {
        var producto = findOrThrow(id);

        if (request.nombre() != null) producto.setNombre(request.nombre());
        if (request.descripcion() != null) producto.setDescripcion(request.descripcion());
        if (request.precio() != null) producto.setPrecio(request.precio());
        if (request.activo() != null) producto.setActivo(request.activo());

        if (request.categoriaId() != null) {
            producto.setCategoria(categoriaRepository.findById(request.categoriaId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.categoriaId().toString())));
        }
        if (request.materialId() != null) {
            producto.setMaterial(materialRepository.findById(request.materialId())
                    .orElseThrow(() -> new MaterialNotFoundException(request.materialId().toString())));
        }

        return ProductoDetailResponse.from(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        var producto = findOrThrow(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private Producto findOrThrow(UUID id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }
}
