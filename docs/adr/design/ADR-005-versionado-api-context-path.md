# ADR-005: Versionado de API REST en Server Context Path

## Status
Accepted

## Context and Problem Statement
El `inventory-service` forma parte de un ecosistema de microservicios que será consumido por un frontend y potencialmente por otros servicios internos. A medida que el sistema evolucione, será necesario introducir cambios en los contratos de la API que podrían ser incompatibles con versiones anteriores. Se necesita una estrategia de versionado que permita esta evolución sin romper a los consumidores existentes.

## Decision Drivers
- Capacidad de evolucionar la API sin romper contratos existentes
- Consistencia del prefijo de versión en todos los endpoints sin duplicar configuración
- Simplicidad de implementación y mantenimiento
- Alineación con estándares REST de nivel empresarial

## Considered Options
- Versionado por header HTTP (`Accept: application/vnd.api.v1+json`)
- Versionado por parámetro de query (`/productos?version=1`)
- Versionado en cada `@RequestMapping` de los controladores (`/v1/productos`)
- Versionado centralizado en el `context-path` del servidor

## Decision Outcome
Se configura el prefijo `/api/v1` a nivel de `server.servlet.context-path` en `application.yml`:

```yaml
server:
  port: 8082
  servlet:
    context-path: /api/v1
```

Todos los controladores definen sus rutas sin prefijo de versión:
```java
@RequestMapping("/productos")   // resulta en /api/v1/productos
@RequestMapping("/categorias")  // resulta en /api/v1/categorias
```

## Consequences

### Positive
- Un solo punto de cambio para toda la versión de la API
- Los controladores no cargan con lógica de versionado
- La URL resultante es clara y predecible para los consumidores: `http://host:8082/api/v1/productos`
- Una futura versión 2 puede implementarse como un módulo o perfil separado sin tocar los controladores existentes

### Negative
- El versionado en `context-path` solo soporta una versión activa por instancia; correr v1 y v2 en paralelo requiere desplegar dos instancias separadas
- No permite versionado a nivel de endpoint individual, solo a nivel de servicio completo
