# ADR-006: Fetch `LAZY` por Defecto en Todas las Relaciones JPA

## Status

Accepted

## Context and Problem Statement

El modelo de dominio del `inventory-service` contiene múltiples relaciones entre entidades (`Producto → Categoria`, `Producto → Material`, `Producto → ImagenProducto[]`, `Promocion → PromocionProducto[]`). La estrategia de carga de estas relaciones determina directamente cuántas queries se ejecutan por request y qué volumen de datos se trae a memoria. Con `FetchType.EAGER`, Hibernate carga automáticamente todas las relaciones al recuperar una entidad, independientemente de si se necesitan en ese contexto.

## Decision Drivers

- Evitar la carga automática de datos no requeridos en cada operación
- Dar control explícito al `Service` sobre qué relaciones cargar según el caso de uso
- Prevenir degradación de performance en endpoints de listado con muchos registros
- Alinear con la recomendación de Hibernate de usar LAZY como estrategia por defecto

## Considered Options

- `FetchType.EAGER` en todas las relaciones (carga automática siempre)
- `FetchType.LAZY` en todas las relaciones (carga bajo demanda)
- Configuración mixta: EAGER en `@ManyToOne` y LAZY en `@OneToMany`

## Decision Outcome

Se aplica `FetchType.LAZY` explícitamente en todas las relaciones de las entidades:

```java
// Producto.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_categoria", nullable = false)
private Categoria categoria;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_material", nullable = false)
private Material material;

@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<ImagenProducto> imagenes;

// PromocionProducto.java
@ManyToOne(fetch = FetchType.LAZY)
private Promocion promocion;

@ManyToOne(fetch = FetchType.LAZY)
private Producto producto;
```

## Consequences

### Positive

- Un `GET /productos` que devuelve 50 registros no ejecuta automáticamente 150 queries adicionales para cargar categorías, materiales e imágenes
- La decisión de cuándo y qué cargar queda en el `Service`, donde existe el contexto del caso de uso
- Reduce significativamente el consumo de memoria en endpoints de listado
- Es la estrategia recomendada por Hibernate para relaciones `@OneToMany`

### Negative

- Genera el problema N+1 cuando el `Service` itera sobre una lista y accede a relaciones lazy sin `JOIN FETCH`: actualmente `convertToResumenDTO()` llama a `imagenProductoRepository` por cada producto, resultando en una query adicional por registro
- Requiere que las sesiones de Hibernate estén abiertas cuando se accede a propiedades lazy; fuera de una transacción activa lanza `LazyInitializationException`
- La solución correcta (`JOIN FETCH` o `@EntityGraph`) no está implementada, dejando la decisión en un estado funcional pero con deuda de performance pendiente
