# ADR-001: Arquitectura en Capas (Layered Architecture)

## Status

Accepted

## Context and Problem Statement

El `inventory-service` necesita una organización interna que permita al equipo de desarrollo navegar, extender y mantener el código de forma predecible. Se requiere una separación clara de responsabilidades entre la recepción de requests HTTP, la lógica de negocio y el acceso a datos, sin introducir una complejidad arquitectónica innecesaria para el tamaño actual del servicio.

## Decision Drivers

- Curva de aprendizaje baja para nuevos integrantes del equipo
- Separación de responsabilidades entre capas
- Facilidad para aplicar testing por capa
- Alineación con los patrones estándar del ecosistema Spring Boot

## Considered Options

- Arquitectura Hexagonal (Ports & Adapters)
- Arquitectura organizada por feature (package-by-feature)
- Arquitectura en Capas clásica (package-by-layer)
- CQRS con separación de modelos de lectura y escritura

## Decision Outcome

Se adopta la arquitectura en capas con la siguiente estructura de paquetes:

```
com.accesoriosdm.inventory
├── controller/   → recepción HTTP, validación de entrada, delegación al service
├── service/      → lógica de negocio, transacciones, orquestación
├── repository/   → acceso a datos, queries JPA/JPQL
├── entity/       → modelo de dominio persistido
├── dto/          → contratos de entrada y salida de la API
└── exception/    → tipos de excepción y handler centralizado
```

La dirección de dependencias es estricta y unidireccional:

```
Controller → Service → Repository → Entity
```

## Consequences

### Positive

- Cualquier desarrollador familiarizado con Spring Boot conoce la estructura sin documentación adicional
- Es sencillo ubicar dónde agregar nueva funcionalidad o corregir un bug
- Las capas pueden testearse de forma independiente (unit test del service, integration test del repository)
- Spring gestiona la inyección de dependencias entre capas de forma nativa

### Negative

- El `Service` acumula responsabilidades mixtas: lógica de negocio y mapeo DTO↔Entity, violando SRP
- La arquitectura no impide que un `Controller` acceda directamente a un `Repository`, ya que no hay un mecanismo de enforcement estructural
- A medida que el dominio crezca, los servicios tienden a convertirse en clases de cientos de líneas sin una subdivisión clara
