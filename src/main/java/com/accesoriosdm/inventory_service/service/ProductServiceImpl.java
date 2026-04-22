package com.accesoriosdm.inventory_service.service;

import com.accesoriosdm.inventory_service.dto.ProductCreateRequest;
import com.accesoriosdm.inventory_service.dto.ProductResponse;
import com.accesoriosdm.inventory_service.dto.ProductUpdateRequest;
import com.accesoriosdm.inventory_service.entity.Product;
import com.accesoriosdm.inventory_service.entity.InventoryStock;
import com.accesoriosdm.inventory_service.exception.NotFoundException;
import com.accesoriosdm.inventory_service.repository.ProductRepository;
import com.accesoriosdm.inventory_service.repository.InventoryStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final InventoryStockRepository stockRepository;

    public ProductServiceImpl(ProductRepository productRepository, InventoryStockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.name);
        product.setDescription(request.description);
        product.setPrice(request.price);
        product.setActive(true);
        
        // Mapear categoría
        Integer categoryId = mapCategoryNameToId(request.category);
        product.setCategoryId(categoryId);
        
        Product saved = productRepository.save(product);
        
        // Crear stock inicial
        InventoryStock stock = new InventoryStock();
        stock.setProductId(saved.getId());
        stock.setQuantity(request.stock != null ? request.stock : 0);
        stock.setLastUpdate(LocalDateTime.now());
        stockRepository.save(stock);
        
        return toResponse(saved, stock);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));
        
        InventoryStock stock = getStockOrEmpty(id);
        return toResponse(product, stock);
    }

   @Override
public List<ProductResponse> list(String material, String category) {
    List<Product> products;

    if (category != null && !category.isBlank()) {
        Integer categoryId = mapCategoryNameToId(category);
        products = productRepository.findByCategoryId(categoryId);
    } else {
        products = productRepository.findAll();
    }

    return products.stream()
            .filter(product -> Boolean.TRUE.equals(product.getActive()))
            .map(product -> {
                ProductResponse response = new ProductResponse();
                response.id = product.getId();
                response.name = product.getName();
                response.description = product.getDescription();
                response.price = product.getPrice();
                response.stock = 0; // temporal para que funcione hoy
                response.category = getCategoryNameById(product.getCategoryId());
                response.material = null;
                response.imageUrl = null;
                return response;
            })
            .collect(Collectors.toList());
}
    @Override
    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));
        
        if (request.name != null) product.setName(request.name);
        if (request.description != null) product.setDescription(request.description);
        if (request.price != null) product.setPrice(request.price);
        if (request.category != null) {
            Integer categoryId = mapCategoryNameToId(request.category);
            product.setCategoryId(categoryId);
        }
        
        Product updated = productRepository.save(product);
        
        // Actualizar stock si viene en la request
        if (request.stock != null) {
            InventoryStock stock = stockRepository.findByProductId(id)
                .orElseGet(() -> {
                    InventoryStock newStock = new InventoryStock();
                    newStock.setProductId(id);
                    return newStock;
                });
            stock.setQuantity(request.stock);
            stock.setLastUpdate(LocalDateTime.now());
            stockRepository.save(stock);
        }
        
        InventoryStock currentStock = getStockOrEmpty(id);
        return toResponse(updated, currentStock);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    // Método auxiliar para obtener stock o uno vacío
    private InventoryStock getStockOrEmpty(Long productId) {
        return stockRepository.findByProductId(productId)
            .orElseGet(() -> {
                InventoryStock emptyStock = new InventoryStock();
                emptyStock.setProductId(productId);
                emptyStock.setQuantity(0);
                emptyStock.setLastUpdate(LocalDateTime.now());
                return emptyStock;
            });
    }

    // Método auxiliar para mapear nombre de categoría a ID
    private Integer mapCategoryNameToId(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            return null;
        }
        
        switch (categoryName.toUpperCase()) {
            case "COLLARES":
                return 1;
            case "PULSERAS":
                return 2;
            case "ANILLOS":
                return 3;
            default:
                return null;
        }
    }

    // Método auxiliar para obtener nombre de categoría
    private String getCategoryNameById(Integer categoryId) {
        if (categoryId == null) return null;
        switch (categoryId) {
            case 1: return "COLLARES";
            case 2: return "PULSERAS";
            case 3: return "ANILLOS";
            default: return "OTROS";
        }
    }

    // Método auxiliar para convertir Entity a Response DTO
    private ProductResponse toResponse(Product product, InventoryStock stock) {
        ProductResponse response = new ProductResponse();
        response.id = product.getId();
        response.name = product.getName();
        response.description = product.getDescription();
        response.price = product.getPrice();
        response.stock = stock != null ? stock.getQuantity() : 0;
        response.category = getCategoryNameById(product.getCategoryId());
        
        // Estos campos no existen en la nueva BD
        response.material = null;
        response.imageUrl = null;
        
        return response;
    }
}