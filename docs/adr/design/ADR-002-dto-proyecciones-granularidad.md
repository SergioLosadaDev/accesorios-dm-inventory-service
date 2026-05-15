# ADR-002: DTO con Proyecciones de Distinta Granularidad

## Status
Accepted

## Context and Problem Statement
El servicio de inventario expone datos de productos tanto para listados de catálogo (muchos registros, consumo desde frontend) como para vistas de detalle individual. Usar una sola representación de datos para ambos casos implica transferir campos innecesarios en listados o perder información en vistas de detalle. Adicionalmente, las entidades JPA contienen campos de control interno (`estado`, `fechaCreacion`, relaciones bidireccionales) que no deben exponerse directamente en la API.

## Decision Drivers
- Desacoplar el modelo de dominio del contrato público de la API
- Minimizar el payload en endpoints de listado
- Evitar la sobreexposición de campos internos de las entidades
- Permitir que el modelo JPA evolucione sin romper a los consumidores

## Considered Options
- Exponer las entidades JPA directamente en los controladores
- Un único DTO genérico para todos los casos de uso
- DTOs con proyecciones diferenciadas por caso de uso

## Decision Outcome
Se implementaron dos niveles de DTO para el recurso `Producto`:

`ProductoResumenDTO` para listados — 6 campos orientados al catálogo:
```java
idProducto, nombre, precio, precioConDescuento, imagenPrincipal, categoriaNombre
```

`ProductoDTO` para detalle completo — 11 campos incluyendo relaciones anidadas:
```java
idProducto, nombre, descripcion, precio, stock, fechaCreacion,
estado, categoria{}, material{}, imagenes[], promocionActiva{}
```

Cada controller elige explícitamente cuál usar según el endpoint:
```java
GET /productos         → List<ProductoResumenDTO>
GET /productos/{id}    → ProductoDTO
```

## Consequences

### Positive
- El frontend recibe exactamente los datos que necesita según el contexto
- Un cambio en la entidad JPA no rompe el contrato de la API
- El payload de listados es significativamente menor que el del detalle
- El campo `estado` y otros campos internos no se exponen en el resumen

### Negative
- Requiere mantener sincronizados múltiples DTOs cuando cambia el modelo
- La conversión manual DTO↔Entity en el `Service` genera código repetitivo (deuda identificada: ausencia de MapStruct)
