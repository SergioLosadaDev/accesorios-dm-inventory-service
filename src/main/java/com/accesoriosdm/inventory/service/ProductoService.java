package com.accesoriosdm.inventory.service;

import com.accesoriosdm.inventory.dto.*;
import com.accesoriosdm.inventory.entity.*;
import com.accesoriosdm.inventory.exception.ResourceNotFoundException;
import com.accesoriosdm.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MaterialRepository materialRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final PromocionProductoRepository promocionProductoRepository;
    private final PromocionRepository promocionRepository;

    @Transactional(readOnly = true)
    public List<ProductoResumenDTO> getAllProductos() {
        log.info("Obteniendo todos los productos activos");
        return productoRepository.findByEstadoTrue().stream()
                .map(this::convertToResumenDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductoResumenDTO> getAllProductosPaginado(Pageable pageable) {
        log.info("Obteniendo productos activos paginados");
        return productoRepository.findByEstadoTrue(pageable)
                .map(this::convertToResumenDTO);
    }

    @Transactional(readOnly = true)
    public ProductoDTO getProductoById(Integer id) {
        log.info("Buscando producto con id: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return convertToFullDTO(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoResumenDTO> getProductosByCategoria(Integer categoriaId) {
        log.info("Buscando productos por categoría: {}", categoriaId);
        return productoRepository.findByCategoriaIdCategoriaAndEstadoTrue(categoriaId).stream()
                .map(this::convertToResumenDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductoResumenDTO> getProductosByCategoriaPaginado(Integer categoriaId, Pageable pageable) {
        log.info("Buscando productos por categoría paginado: {}", categoriaId);
        return productoRepository.findByCategoriaIdCategoriaAndEstadoTrue(categoriaId, pageable)
                .map(this::convertToResumenDTO);
    }

    @Transactional(readOnly = true)
    public List<ProductoResumenDTO> getProductosDisponibles() {
        log.info("Obteniendo productos disponibles (con stock)");
        return productoRepository.findProductosDisponibles().stream()
                .map(this::convertToResumenDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoResumenDTO> getProductosDisponiblesByCategoria(Integer categoriaId) {
        log.info("Obteniendo productos disponibles por categoría: {}", categoriaId);
        categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + categoriaId));
        
        return productoRepository.findProductosDisponiblesByCategoria(categoriaId).stream()
                .map(this::convertToResumenDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoResumenDTO> searchProductosByNombre(String nombre) {
        log.info("Buscando productos por nombre: {}", nombre);
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToResumenDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoDTO createProducto(ProductoDTO request) {
        log.info("Creando nuevo producto: {}", request.getNombre());
        
        Categoria categoria = categoriaRepository.findById(request.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        
        Material material = materialRepository.findById(request.getMaterial().getIdMaterial())
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));
        
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setEstado(request.getEstado());
        producto.setCategoria(categoria);
        producto.setMaterial(material);
        
        Producto saved = productoRepository.save(producto);
        log.info("Producto creado con id: {}", saved.getIdProducto());
        
        // Procesar imágenes si vienen en el request
        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            for (ImagenProductoDTO imgDTO : request.getImagenes()) {
                ImagenProducto imagen = new ImagenProducto();
                imagen.setUrlImagen(imgDTO.getUrlImagen());
                imagen.setOrden(imgDTO.getOrden() != null ? imgDTO.getOrden() : 1);
                imagen.setProducto(saved);
                imagenProductoRepository.save(imagen);
            }
        }
        
        return convertToFullDTO(saved);
    }

    @Transactional
    public ProductoDTO updateProducto(Integer id, ProductoDTO request) {
        log.info("Actualizando producto con id: {}", id);
        
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        
        Categoria categoria = categoriaRepository.findById(request.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        
        Material material = materialRepository.findById(request.getMaterial().getIdMaterial())
                .orElseThrow(() -> new ResourceNotFoundException("Material no encontrado"));
        
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setEstado(request.getEstado());
        producto.setCategoria(categoria);
        producto.setMaterial(material);
        
        Producto updated = productoRepository.save(producto);
        log.info("Producto actualizado con id: {}", updated.getIdProducto());
        
        return convertToFullDTO(updated);
    }

    @Transactional
    public void deleteProducto(Integer id) {
        log.info("Eliminando producto con id: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
        log.info("Producto eliminado con id: {}", id);
    }

    @Transactional(readOnly = true)
    public ImagenProductoDTO addImagenToProducto(Integer productoId, String urlImagen, Integer orden) {
        log.info("Agregando imagen al producto: {}", productoId);
        
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + productoId));
        
        ImagenProducto imagen = new ImagenProducto();
        imagen.setUrlImagen(urlImagen);
        imagen.setOrden(orden != null ? orden : 1);
        imagen.setProducto(producto);
        
        ImagenProducto saved = imagenProductoRepository.save(imagen);
        log.info("Imagen agregada con id: {}", saved.getIdImagen());
        
        ImagenProductoDTO dto = new ImagenProductoDTO();
        dto.setIdImagen(saved.getIdImagen());
        dto.setUrlImagen(saved.getUrlImagen());
        dto.setOrden(saved.getOrden());
        return dto;
    }

    @Transactional
    public void deleteImagenFromProducto(Integer imagenId) {
        log.info("Eliminando imagen con id: {}", imagenId);
        imagenProductoRepository.deleteById(imagenId);
        log.info("Imagen eliminada con id: {}", imagenId);
    }

    @Transactional(readOnly = true)
    public List<ImagenProductoDTO> getImagenesByProducto(Integer productoId) {
        log.info("Obteniendo imágenes del producto: {}", productoId);
        return imagenProductoRepository.findByProductoIdProductoOrderByOrdenAsc(productoId).stream()
                .map(imagen -> {
                    ImagenProductoDTO dto = new ImagenProductoDTO();
                    dto.setIdImagen(imagen.getIdImagen());
                    dto.setUrlImagen(imagen.getUrlImagen());
                    dto.setOrden(imagen.getOrden());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private ProductoResumenDTO convertToResumenDTO(Producto producto) {
        ProductoResumenDTO dto = new ProductoResumenDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        
        // Obtener la primera imagen del producto (principal)
        List<ImagenProducto> imagenes = imagenProductoRepository.findByProductoIdProductoOrderByOrdenAsc(producto.getIdProducto());
        if (!imagenes.isEmpty()) {
            dto.setImagenPrincipal(imagenes.get(0).getUrlImagen());
        }
        
        if (producto.getCategoria() != null) {
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }
        
        // Calcular precio con descuento si hay promoción vigente
        BigDecimal precioConDescuento = calcularPrecioConDescuento(producto);
        dto.setPrecioConDescuento(precioConDescuento);
        
        return dto;
    }

    private ProductoDTO convertToFullDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setEstado(producto.getEstado());
        
        // Categoría
        if (producto.getCategoria() != null) {
            CategoriaDTO catDTO = new CategoriaDTO();
            catDTO.setIdCategoria(producto.getCategoria().getIdCategoria());
            catDTO.setNombre(producto.getCategoria().getNombre());
            catDTO.setDescripcion(producto.getCategoria().getDescripcion());
            dto.setCategoria(catDTO);
        }
        
        // Material
        if (producto.getMaterial() != null) {
            MaterialDTO matDTO = new MaterialDTO();
            matDTO.setIdMaterial(producto.getMaterial().getIdMaterial());
            matDTO.setNombre(producto.getMaterial().getNombre());
            matDTO.setDescripcion(producto.getMaterial().getDescripcion());
            dto.setMaterial(matDTO);
        }
        
        // Imágenes
        List<ImagenProducto> imagenes = imagenProductoRepository.findByProductoIdProductoOrderByOrdenAsc(producto.getIdProducto());
        dto.setImagenes(imagenes.stream().map(img -> {
            ImagenProductoDTO imgDTO = new ImagenProductoDTO();
            imgDTO.setIdImagen(img.getIdImagen());
            imgDTO.setUrlImagen(img.getUrlImagen());
            imgDTO.setOrden(img.getOrden());
            return imgDTO;
        }).collect(Collectors.toList()));
        
        // Promoción activa
        BigDecimal precioConDescuento = calcularPrecioConDescuento(producto);
        dto.setPrecioConDescuento(precioConDescuento);
        
        // Buscar promoción activa para mostrar
        List<Promocion> promociones = promocionRepository.findPromocionesVigentesByProducto(producto.getIdProducto(), LocalDateTime.now());
        if (!promociones.isEmpty()) {
            Promocion promocion = promociones.get(0);
            PromocionDTO promocionDTO = new PromocionDTO();
            promocionDTO.setIdPromocion(promocion.getIdPromocion());
            promocionDTO.setNombre(promocion.getNombre());
            promocionDTO.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
            promocionDTO.setFechaInicio(promocion.getFechaInicio());
            promocionDTO.setFechaFin(promocion.getFechaFin());
            promocionDTO.setActivo(promocion.getActivo());
            dto.setPromocionActiva(promocionDTO);
        }
        
        return dto;
    }

    private BigDecimal calcularPrecioConDescuento(Producto producto) {
        List<Promocion> promocionesVigentes = promocionRepository.findPromocionesVigentesByProducto(
                producto.getIdProducto(), LocalDateTime.now());
        
        if (!promocionesVigentes.isEmpty()) {
            Promocion promocion = promocionesVigentes.get(0);
            BigDecimal descuento = producto.getPrecio()
                    .multiply(promocion.getPorcentajeDescuento())
                    .divide(BigDecimal.valueOf(100));
            return producto.getPrecio().subtract(descuento);
        }
        
        return producto.getPrecio();
    }
}