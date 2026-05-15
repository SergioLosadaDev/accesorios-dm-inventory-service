# ADR-003: Separación de Schemas PostgreSQL por Dominio de Negocio

## Status
Accepted

## Context and Problem Statement
El modelo de datos del `inventory-service` abarca conceptos de dos dominios distintos: el catálogo de productos (productos, categorías, materiales, imágenes) y las promociones comerciales (promociones y su relación con productos). Colocar todas las tablas en el schema `public` por defecto genera acoplamiento estructural en la base de datos y dificulta la gestión de permisos, el monitoreo y una eventual separación de servicios.

## Decision Drivers
- Separar lógicamente los dominios de negocio en la capa de persistencia
- Facilitar la gestión de permisos a nivel de schema en PostgreSQL
- Anticipar una posible extracción del dominio de promociones a su propio servicio
- Evitar colisiones de nombres entre tablas de diferentes dominios

## Considered Options
- Una sola base de datos con todas las tablas en el schema `public`
- Schemas separados por dominio dentro de la misma base de datos
- Bases de datos completamente separadas por dominio desde el inicio

## Decision Outcome
Se definen dos schemas en PostgreSQL, asignados mediante la anotación `@Table` en cada entidad:

```java
// Dominio: Catálogo
@Table(name = "producto",           schema = "catalogo")
@Table(name = "categoria",          schema = "catalogo")
@Table(name = "material",           schema = "catalogo")
@Table(name = "imagen_producto",    schema = "catalogo")

// Dominio: Promociones
@Table(name = "promocion",          schema = "promociones")
@Table(name = "promocion_producto", schema = "promociones")
```

Se elige schemas dentro de la misma base de datos (en lugar de bases de datos separadas) para mantener la capacidad de hacer JOINs directos mientras el servicio sea monolítico.

## Consequences

### Positive
- Los límites del dominio quedan codificados en la estructura de la base de datos, no solo en el código
- Es posible asignar roles y permisos de PostgreSQL a nivel de schema de forma independiente
- Si el dominio `promociones` crece en complejidad, puede migrarse a su propia base de datos con impacto mínimo en el schema
- Las queries son explícitas sobre qué dominio están accediendo

### Negative
- Requiere que el usuario de base de datos tenga permisos sobre múltiples schemas
- `ddl-auto: validate` debe conocer ambos schemas; una mala configuración de `search_path` en PostgreSQL puede causar errores de validación al arrancar
- Los JOINs entre schemas son válidos pero incrementan el acoplamiento si se hacen desde el exterior del servicio
