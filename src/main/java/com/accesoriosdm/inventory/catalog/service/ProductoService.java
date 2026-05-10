package com.accesoriosdm.inventory.catalog.service;

import com.accesoriosdm.inventory.catalog.dto.ImagenProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoCreateRequest;
import com.accesoriosdm.inventory.catalog.dto.ProductoDetailResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoResponse;
import com.accesoriosdm.inventory.catalog.dto.ProductoUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    Page<ProductoResponse> listar(Integer categoriaId, Integer materialId,
                                  String nombre, Boolean estado, Pageable pageable);

    ProductoDetailResponse obtener(Integer id);

    List<ImagenProductoResponse> listarImagenes(Integer productoId);

    ProductoDetailResponse crear(ProductoCreateRequest request);

    ProductoDetailResponse actualizar(Integer id, ProductoUpdateRequest request);

    void eliminar(Integer id);
}
