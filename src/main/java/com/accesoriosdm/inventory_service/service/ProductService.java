package com.accesoriosdm.inventory_service.service;

import java.util.List;

import com.accesoriosdm.inventory_service.dto.ProductCreateRequest;
import com.accesoriosdm.inventory_service.dto.ProductResponse;
import com.accesoriosdm.inventory_service.dto.ProductUpdateRequest;

public interface ProductService {
    ProductResponse create(ProductCreateRequest request);
    ProductResponse getById(Long id);
    List<ProductResponse> list(String material, String category);
    ProductResponse update(Long id, ProductUpdateRequest request);
    void delete(Long id);
}