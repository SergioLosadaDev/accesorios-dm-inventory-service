# ADR-004: Soft Delete mediante Campo `estado`

## Status
Accepted

## Context and Problem Statement
En un sistema de inventario de e-commerce, eliminar físicamente un registro de producto o categoría puede romper referencias históricas (órdenes pasadas, reportes, imágenes asociadas) y constituye una operación irreversible. Se necesita una estrategia que permita "desactivar" entidades del catálogo sin destruir su información ni sus relaciones.

## Decision Drivers
- Preservar la integridad referencial histórica
- Permitir la recuperación ante eliminaciones accidentales
- Mantener consistencia en las consultas — los registros inactivos no deben aparecer en el catálogo público
- Simplicidad de implementación en el modelo JPA

## Considered Options
- Hard delete (`DELETE FROM tabla WHERE id = ?`) sin preservación de datos
- Soft delete con campo `deleted_at` (timestamp de eliminación)
- Soft delete con campo booleano `estado`
- Tabla de auditoría separada para registros eliminados

## Decision Outcome
Se agrega el campo `estado: Boolean` a las entidades `Producto` y `Categoria`, con valor por defecto `true`. La eliminación lógica consiste en cambiar este campo a `false`.

```java
@Column(nullable = false)
private Boolean estado = true;
```

Todas las consultas públicas filtran exclusivamente por registros activos:
```java
List<Producto> findByEstadoTrue();
Page<Producto> findByEstadoTrue(Pageable pageable);
findByCategoriaIdCategoriaAndEstadoTrue(Integer categoriaId);

// JPQL explícito para disponibilidad
WHERE p.estado = true AND p.stock > 0
```

`Categoria` implementa además un endpoint explícito de cambio de estado:
```java
PATCH /categorias/{id}/toggle-estado
```

## Consequences

### Positive
- Un producto "eliminado" conserva su nombre, precio e imágenes; las referencias históricas permanecen válidas
- La operación es reversible: cambiar `estado` de `false` a `true` restaura el registro
- El catálogo público nunca muestra productos inactivos sin necesidad de lógica adicional en el frontend
- Protege ante errores humanos en operaciones administrativas

### Negative
- Existe una inconsistencia activa: `deleteProducto()` en `ProductoService` ejecuta `productoRepository.delete()` (hard delete) en lugar de `producto.setEstado(false)`, violando este ADR
- Las tablas acumulan registros inactivos indefinidamente; en catálogos grandes puede ser necesario un proceso de archivado
- Queries sin el filtro `estado = true` pueden devolver resultados inesperados si el desarrollador no conoce la convención
