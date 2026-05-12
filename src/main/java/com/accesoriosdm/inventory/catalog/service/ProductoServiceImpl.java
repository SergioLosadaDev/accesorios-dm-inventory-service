package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.ImagenProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.ProductoDetailResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoUpdateRequest;
import com.accesoriosdm.inventory.catalog.entity.Producto;
import com.accesoriosdm.inventory.catalog.repository.CategoriaRepository;
import com.accesoriosdm.inventory.catalog.repository.ImagenProductoRepository;
import com.accesoriosdm.inventory.catalog.repository.MaterialRepository;
import com.accesoriosdm.inventory.catalog.repository.ProductoRepository;
import com.accesoriosdm.inventory.catalog.specification.ProductoSpecification;
import com.accesoriosdm.inventory.exception.CategoryNotFoundException;
import com.accesoriosdm.inventory.exception.MaterialNotFoundException;
import com.accesoriosdm.inventory.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MaterialRepository materialRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponse> listar(Integer categoriaId, Integer materialId,
                                         String nombre, Boolean estado, Pageable pageable) {
        return productoRepository
                .findAll(ProductoSpecification.withFilters(categoriaId, materialId, nombre, estado), pageable)
                .map(ProductoResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDetailResponse obtener(Integer id) {
        Producto p = productoRepository.findByIdWithImagenes(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
        return ProductoDetailResponse.from(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImagenProductoResponse> listarImagenes(Integer productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new ProductNotFoundException(productoId.toString());
        }
        return imagenProductoRepository.findByProductoIdOrderByOrdenAsc(productoId)
                .stream().map(ImagenProductoResponse::from).toList();
    }

    @Override
    @Transactional
    public ProductoDetailResponse crear(ProductoCreateRequest request) {
        var categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoriaId().toString()));

        var material = materialRepository.findById(request.materialId())
                .orElseThrow(() -> new MaterialNotFoundException(request.materialId().toString()));

        var producto = Producto.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .precio(request.precio())
                .categoria(categoria)
                .material(material)
                .estado(true)
                .build();

        return ProductoDetailResponse.from(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public ProductoDetailResponse actualizar(Integer id, ProductoUpdateRequest request) {
        var producto = findOrThrow(id);

        if (request.nombre() != null) producto.setNombre(request.nombre());
        if (request.descripcion() != null) producto.setDescripcion(request.descripcion());
        if (request.precio() != null) producto.setPrecio(request.precio());
        if (request.estado() != null) producto.setEstado(request.estado());

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
    public void eliminar(Integer id) {
        var producto = findOrThrow(id);
        producto.setEstado(false);
        productoRepository.save(producto);
    }

    private Producto findOrThrow(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }
}
