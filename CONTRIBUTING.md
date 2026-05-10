# Guía de Contribución — Inventory Service

> Servicio: `accesorios-dm-inventory-service` | Stack: Spring Boot 3 + Java 21 | Puerto: `8082`

---

## 1. Requisitos previos

- Java 21 (JDK)
- Maven 3.9+ o usar el wrapper `./mvnw`
- Docker y Docker Compose
- Git configurado con tu nombre y email
- Acceso al repositorio en GitHub

---

## 2. Setup local

```bash
# Clonar el repositorio
git clone https://github.com/SergioLosadaDev/accesorios-dm-inventory-service.git
cd accesorios-dm-inventory-service

# Copiar variables de entorno
cp .env.example .env
# Editar .env con los valores correctos para desarrollo local

# Levantar con Docker Compose (desde el directorio raíz del workspace)
docker-compose up inventory-service

# O en modo desarrollo local (sin Docker)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 3. Estrategia de ramas

El proyecto tiene tres ramas permanentes que representan los tres ambientes:

| Rama | Ambiente | Descripción |
|---|---|---|
| `main` | Producción | Solo recibe merges desde `qa` con aprobación manual |
| `qa` | QA / Testing | Solo recibe merges desde `develop` al cerrar un ciclo |
| `develop` | Desarrollo | Integración continua. Base para todas las HUs |

**Ninguna de estas ramas acepta push directo.** Todo cambio entra por Pull Request.

---

## 4. Convención de ramas de feature

```
HU-DEV-SALB_XX
```

Donde `XX` es el número de la Historia de Usuario (con cero a la izquierda).

```bash
# Crear rama desde develop actualizado
git checkout develop
git pull origin develop
git checkout -b HU-DEV-SALB_10
```

---

## 5. Convención de commits

Se usa [Conventional Commits](https://www.conventionalcommits.org/):

```
<tipo>(<scope>): <descripción en imperativo, minúsculas>
```

**Tipos:** `feat`, `fix`, `refactor`, `test`, `docs`, `chore`, `ci`

**Scope:** ID de la HU o módulo afectado.

```bash
# Ejemplos correctos
git commit -m "feat(HU-DEV-SALB_14): add product CRUD endpoints"
git commit -m "fix(HU-DEV-SALB_11): map MethodArgumentNotValidException to 422"
git commit -m "test(HU-DEV-SALB_14): add product service unit tests"
git commit -m "chore(deps): update spring-boot to 3.2.5"

# Ejemplos incorrectos
git commit -m "cambios"
git commit -m "fix stuff"
git commit -m "WIP"
git commit -m "Update code"
```

---

## 6. Proceso de Pull Request

1. Crear la rama desde `develop` con el nombre `HU-DEV-SALB_XX`.
2. Desarrollar con commits atómicos.
3. Hacer push y abrir PR en GitHub usando la plantilla disponible.
4. El título del PR debe ser: `[HU-DEV-SALB_XX] Título de la HU`.
5. Asignar al menos 1 reviewer.
6. El CI debe pasar antes de la aprobación.
7. Tras la aprobación, el **autor** hace el merge (Squash and Merge hacia `develop`).
8. El autor elimina la rama remota tras el merge:
   ```bash
   git push origin --delete HU-DEV-SALB_XX
   git branch -d HU-DEV-SALB_XX
   ```

---

## 7. Estándares técnicos del servicio

Estos estándares son de cumplimiento obligatorio en todo el código del servicio:

| Estándar | Referencia |
|---|---|
| Prefijo de rutas `/api/v1/` | ADR-008 |
| Errores en formato estándar del sistema via `GlobalExceptionHandler` | ADR-009 |
| Solo acceso a schemas `catalogo` e `inventario` | ADR-005 |
| Sin JOINs cross-schema en ninguna query | ADR-005 |
| `spring.jpa.hibernate.ddl-auto=validate` — Liquibase gestiona el schema | ADR-001 |
| Precios y cantidades monetarias usan `BigDecimal`, no `double` | Dominio |
| El `userId` se lee del header `X-User-Id`, no del body del request | ADR-003 |
| Sin secretos en el código — siempre variables de entorno / `application.yml` | Seguridad |
| Usar DTOs de respuesta — nunca exponer entidades JPA directamente | Clean Code |

---

## 8. Estructura de paquetes

```
com.accesorios.dm.inventory/
├── InventoryServiceApplication.java
├── config/
│   └── JpaConfig.java
├── common/
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java   ← @ControllerAdvice (ADR-009)
│   │   └── domain/                       ← Excepciones de dominio
│   └── dto/
│       └── ErrorResponseDto.java
├── catalog/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   └── dto/
└── inventory/
    ├── controller/
    ├── service/
    ├── repository/
    ├── entity/
    └── dto/
```

---

## 9. Definición de Done

Un PR puede mergearse a `develop` cuando:

- [ ] Los criterios de aceptación de la HU están cumplidos.
- [ ] El servicio levanta con `docker-compose up inventory-service`.
- [ ] El build de Maven pasa sin errores (`./mvnw clean package`).
- [ ] Al menos 1 reviewer aprobó el PR.
- [ ] No hay secretos ni credenciales en el código.
- [ ] Los estándares técnicos de la sección 7 se cumplen.
- [ ] Solo se accede a los schemas `catalogo` e `inventario`.

---

## 10. Variables de entorno

Ver `application.yml` para la lista completa. Las variables críticas son:

```
SERVER_PORT         Puerto del servidor (default: 8082)
DB_HOST             Host de PostgreSQL
DB_PORT             Puerto de PostgreSQL (default: 5432)
DB_NAME             Nombre de la base de datos
DB_USERNAME         Usuario de BD (debe ser svc_inventory)
DB_PASSWORD         Contraseña del usuario de BD
```

> ⚠️ **Nunca commitear archivos con credenciales**. Usar variables de entorno siempre.

---

## 11. Recursos

- [Backlog de HUs](../accesorios-dm/docs/HUs/)
- [ADRs del proyecto](../accesorios-dm/docs/ADRs-v2/)
- [Git Strategy completo](../accesorios-dm/docs/git-strategy/GIT-STRATEGY.md)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
