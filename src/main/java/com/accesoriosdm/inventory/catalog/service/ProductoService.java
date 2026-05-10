package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.ImagenProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.ProductoDetailResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductoService {

    Page<ProductoResponse> listar(UUID categoriaId, UUID materialId,
                                  String nombre, Boolean activo, Pageable pageable);

    ProductoDetailResponse obtener(UUID id);

    List<ImagenProductoResponse> listarImagenes(UUID productoId);

    ProductoDetailResponse crear(ProductoCreateRequest request);

    ProductoDetailResponse actualizar(UUID id, ProductoUpdateRequest request);

    void eliminar(UUID id);
}
