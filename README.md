# Sistema de Gestión de Peluquería – Microservicios

Evaluación Parcial N°2 – JVY0101 *Java: Diseño y Construcción de Soluciones Nativas en Nube*.

## Integrantes

- Daniel Azocar
- Carolina Adaros

---

## 1. Descripción del proyecto

Sistema de gestión para una peluquería, construido con arquitectura de **microservicios** en **Spring Boot 3.4.3 (Java 21)**, **Spring Cloud** (Eureka y Gateway), autenticación con **JWT** y base de datos relacional **Oracle Autonomous Database**.

El dominio cubre clientes, profesionales, tipos de servicio, disponibilidad horaria de los profesionales y la agenda de citas, además de autenticación, registro de atenciones y módulos de inventario y ventas (productos, bodegas, kardex, boletas, pagos y notificaciones).

Cada entidad del dominio es un microservicio independiente (proyecto Maven propio), registrado en Eureka y expuesto a través de un API Gateway único.

---

## 2. Microservicios implementados

| Microservicio | Descripción | Puerto | Tabla(s) Oracle |
|---|---|---|---|
| **eureka** | Servidor de descubrimiento (Service Registry) | 8761 | – |
| **gateway** | API Gateway, punto de entrada único | 8080 | – |
| **tipoServicio** | Catálogo de servicios (corte, color, etc.) | 8081 | `TIPO_SERVICIOS` |
| **profesional** | Profesionales y sus especialidades | 8082 | `Profesional` |
| **disponibilidadProfesional** | Disponibilidad horaria de cada profesional | 8083 | `DISPONIBILIDAD_PROFESIONAL` |
| **cliente** | Clientes | 8084 | `CLIENTE` |
| **agenda** | Citas; valida cliente, tipo de servicio y disponibilidad | 8085 | `Agenda` |
| **auth** | Login, registro de usuarios y emisión de JWT | 8086 | `USUARIO` |
| **registro** | Registro de atenciones | 8087 | `REGISTRO` |
| **producto** | Catálogo de productos | 8088 | `PRODUCTO` |
| **bodega** | Bodegas | 8089 | `BODEGA` |
| **kardex** | Movimientos y stock; consulta a producto y bodega | 8090 | `MOVIMIENTO_KARDEX` |
| **boleta** | Boletas y sus detalles | 8091 | `BOLETA`, `DETALLE_BOLETA` |
| **pago** | Pagos de boletas | 8092 | `PAGO` |
| **notificacion** | Notificaciones a clientes | 8093 | `NOTIFICACION` |

Los servicios `kardex`, `boleta`, `pago` y `notificacion` registran hechos históricos, por lo que exponen creación y consulta, sin modificación ni eliminación. `auth` expone login, registro y consulta del usuario autenticado.

---

## 3. Arquitectura

### 3.1 Capas de cada microservicio

Todos los microservicios de negocio siguen la misma estructura:

```
<servicio>/src/main/java/com/peluqueria/<servicio>/
├── Controller/   Endpoints REST (@RestController, @RequestMapping)
├── Service/      Lógica de negocio y validaciones
├── Repository/   Acceso a datos (extiende JpaRepository)
├── Model/        Entidades JPA
├── dto/          DTOs de entrada y salida con Bean Validation
├── Exception/    Manejador global de errores (@RestControllerAdvice)
├── Security/     Filtro y servicio JWT
└── Config/       Seguridad, Swagger, WebClient, carga inicial de datos
```

Se usa inyección de dependencias por constructor (Lombok `@RequiredArgsConstructor`), DTOs para no exponer las entidades y documentación de endpoints con springdoc/Swagger.

### 3.2 Relaciones entre entidades

- **Entre microservicios:** cada servicio es dueño de sus tablas y se relaciona con los demás por **identificador o RUT** (por ejemplo, `Agenda` guarda `idTipoServicio`, `rutCliente` y `rutProfesional`) y los valida consultando al otro servicio con `WebClient`. No hay claves foráneas entre servicios.
- **Dentro de un servicio:** `Boleta` tiene muchos `DetalleBoleta` (`@OneToMany(mappedBy = "boleta", cascade = ALL, orphanRemoval = true)`), y cada `DetalleBoleta` pertenece a una boleta (`@ManyToOne` con `@JoinColumn(name = "id_boleta")`).

### 3.3 Manejo de errores

Los servicios que consultan a otros (por ejemplo `agenda` y `disponibilidadProfesional`) distinguen el tipo de error:

| Código | Cuándo ocurre |
|---|---|
| 400 | Datos inválidos (falla `@Valid`, con el detalle por campo) |
| 401 | Falta el token JWT o es inválido o expiró |
| 403 | `DELETE` con un usuario que no es `ADMIN` |
| 404 | El recurso no existe (cliente, tipo de servicio, profesional, cita) |
| 409 | Conflicto: el profesional ya tiene una cita activa en ese horario o no tiene disponibilidad |
| 503 | Otro microservicio del que depende no responde |
| 500 | Error interno inesperado |

En `agenda`, una cita con estado **Cancelada** no cuenta como horario ocupado.

---

## 4. Conexión a la base de datos

La persistencia usa **Spring Data JPA (Hibernate)** sobre **Oracle Autonomous Database**.

**Dependencias en el `pom.xml`** (iguales en todos los microservicios de negocio):

- `spring-boot-starter-data-jpa`
- `ojdbc11`, `oraclepki`, `osdt_core`, `osdt_cert` (driver Oracle y soporte de la wallet)
- `spring-boot-starter-web`, `-validation`, `-security`, `-webflux` (WebClient), `-actuator`
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` (JWT)
- `springdoc-openapi-starter-webmvc-ui` (Swagger)
- `spring-cloud-starter-netflix-eureka-client`
- En scope `test`: `spring-boot-starter-test`, `junit-jupiter-engine`, `mockito-core`, `h2`, `datafaker`
- Plugins: `spring-boot-maven-plugin` (jar ejecutable) y `maven-compiler-plugin`. El jar final se llama `app.jar` (`<finalName>app</finalName>`).

**Configuración en `src/main/resources/application.yaml`** de cada servicio:

```yaml
spring:
  datasource:
    driver-class-name: oracle.jdbc.OracleDriver
    url: "jdbc:oracle:thin:@a6etg25wwemt8hfb_high?TNS_ADMIN=${WALLET_PATH:/app/wallet}"
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    database-platform: org.hibernate.dialect.OracleDialect
    hibernate:
      ddl-auto: update
```

- El usuario y la clave **no están en el código**: se leen desde variables de entorno (archivo `.env`).
- `ddl-auto: update` crea y ajusta las tablas a partir de las entidades al iniciar.
- La **wallet** de Oracle se monta en los contenedores en `/app/wallet`. Se descarga desde Oracle Cloud, **no se incluye en el repositorio** y debe quedar en la raíz del proyecto como `Wallet_A6ETG25WWEMT8HFB/`.

---

## 5. Variables de entorno

Se definen en un archivo `.env` en la raíz del proyecto (no se sube a Git). Se parte de `.env.example`:

| Variable | Descripción |
|---|---|
| `DB_USERNAME` | Usuario de Oracle (`PELUQUERIA`) |
| `DB_PASSWORD` | Clave del usuario de Oracle |
| `JWT_SECRET` | Secreto para firmar los tokens (generar con `openssl rand -base64 32`) |
| `JWT_EXPIRATION_MS` | Duración del token en milisegundos (por defecto 900000 = 15 min) |
| `ADMIN_USERNAME` | Usuario administrador inicial de `auth` |
| `ADMIN_PASSWORD` | Clave del administrador inicial de `auth` |

El administrador se crea al iniciar `auth` solo si la tabla `USUARIO` está vacía.

---

## 6. Instrucciones de ejecución

### Requisitos previos

- Git
- Docker y Docker Compose
- JDK 21 (solo para compilar con Maven fuera de Docker)
- La carpeta `Wallet_A6ETG25WWEMT8HFB/` descargada desde Oracle Cloud y las credenciales de la base de datos
- Postman (para probar los endpoints)

No hace falta instalar Maven: todos los módulos incluyen **Maven Wrapper** (`mvnw`, `mvnw.cmd`).

### Paso 1. Clonar el repositorio

```bash
git clone https://github.com/caadaros/Peluqueria.git
cd Peluqueria
```

### Paso 2. Configurar las variables de entorno

```bash
cp .env.example .env          # en Windows (CMD): copy .env.example .env
```

Edite `.env` y complete los valores reales (ver sección 5).

### Paso 3. Copiar la wallet de Oracle

Copie la carpeta `Wallet_A6ETG25WWEMT8HFB/` a la raíz del proyecto (al lado de `docker-compose.yaml`). El compose la monta en `/app/wallet` en cada contenedor.

### Paso 4. Levantar el sistema con Docker Compose

```bash
docker compose up --build
```

Docker compila cada microservicio dentro de su propia imagen, por lo que no hay que instalar dependencias a mano. La primera vez demora varios minutos.

Para detener el sistema:

```bash
docker compose down
```

Para reconstruir un solo servicio después de un cambio:

```bash
docker compose up --build cliente
```

### Paso 5. Verificar que todo está arriba

1. **Eureka:** http://localhost:8761 debe listar los microservicios registrados.
2. **Swagger (Gateway):** http://localhost:8080/swagger-ui.html muestra la documentación de cliente, profesional, tipo de servicio, disponibilidad y agenda. Cada servicio también tiene su propio Swagger en `http://localhost:<puerto>/swagger-ui.html`.

---

## 7. Compilar, probar y empaquetar con Maven

Cada microservicio es un proyecto Maven independiente. Ejemplo con `agenda`:

```bash
cd agenda
./mvnw clean            # elimina la carpeta target
./mvnw test             # ejecuta las pruebas unitarias (JUnit 5 + Mockito)
./mvnw package          # compila, prueba y genera target/app.jar
./mvnw install          # además copia el jar al repositorio local (~/.m2)
```

En Windows use `mvnw.cmd` en lugar de `./mvnw`.

- El resultado es `target/app.jar`, un jar ejecutable de Spring Boot listo para ejecutarse (`java -jar target/app.jar`) o desplegarse.
- Las pruebas **no se omiten** en estos comandos. Para compilar sin pruebas se usa `-DskipTests`; los Dockerfiles lo usan solo para acelerar la construcción de las imágenes.

### Ejecutar un servicio fuera de Docker (opcional)

Se recomienda Docker, porque varios servicios (`agenda`, `disponibilidadProfesional`, `registro`, `kardex`, `pago`, `notificacion`) consultan a otros y necesitan que estén arriba. Para un servicio individual:

```bash
export EUREKA_URL=http://localhost:8761/eureka/
export WALLET_PATH=/ruta/local/Wallet_A6ETG25WWEMT8HFB
export DB_USERNAME=PELUQUERIA DB_PASSWORD=... JWT_SECRET=...
java -jar target/app.jar
```

(En PowerShell, use `$env:NOMBRE="valor"` en lugar de `export`.)

---

## 8. Rutas del Gateway y pruebas con Postman

Todas las llamadas van al Gateway (`http://localhost:8080`). El Gateway enruta con balanceo (`lb://`) hacia el servicio registrado en Eureka. Todas las rutas `/api/**` exigen un token JWT (`Authorization: Bearer <token>`) y los `DELETE` exigen rol `ADMIN`.

| Ruta | Microservicio |
|---|---|
| `/auth/**` | auth |
| `/api/cliente/**` | cliente |
| `/api/profesional/**` | profesional |
| `/api/tiposervicio/**` | tipoServicio |
| `/api/disponibilidad/**` | disponibilidadProfesional |
| `/api/agenda/**` | agenda |
| `/api/registro/**` | registro |
| `/api/producto/**` | producto |
| `/api/bodega/**` | bodega |
| `/api/kardex/**` | kardex |
| `/api/boleta/**` | boleta |
| `/api/pago/**` | pago |
| `/api/notificacion/**` | notificacion |

### 8.1 Obtener el token

```
POST http://localhost:8080/auth/login
Content-Type: application/json

{ "username": "admin", "password": "<ADMIN_PASSWORD del .env>" }
```

Responde `200` con el token. Úselo en las siguientes peticiones como **Bearer Token**. Otros endpoints de `auth`: `POST /auth/register` (solo `ADMIN`) y `GET /auth/me`.

### 8.2 CRUD de ejemplo: Cliente

| Operación | Petición | Resultado esperado |
|---|---|---|
| Crear | `POST /api/cliente` | `201` y el cliente creado |
| Listar | `GET /api/cliente` | `200` con la lista |
| Consultar | `GET /api/cliente/{rut}` | `200` con los datos |
| Actualizar | `PUT /api/cliente/{rut}` | `200` con los datos modificados |
| Eliminar | `DELETE /api/cliente/{rut}` (token `ADMIN`) | `200` y el registro desaparece |

Para comprobar la persistencia, consulte la tabla en Oracle después de cada operación, por ejemplo `SELECT * FROM CLIENTE;`. Las tablas `Agenda` y `Profesional` se crearon con nombre en minúsculas, por lo que deben consultarse entre comillas: `SELECT * FROM "Agenda";`.

### 8.3 Casos de error de ejemplo: Agenda

Cuerpo de una cita (`POST /api/agenda`):

```json
{
  "fecha": "2026-08-10",
  "horaInicio": "09:00",
  "idTipoServicio": 1,
  "rutProfesional": "11111111-1",
  "rutCliente": "22222222-2",
  "estadoCita": "Confirmado"
}
```

Para que la cita se cree, el profesional debe tener una disponibilidad cargada para esa fecha y hora (`POST /api/disponibilidad`), y deben existir el cliente y el tipo de servicio.

| Caso | Cómo provocarlo | Resultado |
|---|---|---|
| Datos inválidos | `POST` con campos obligatorios en blanco | `400` con el error por campo |
| Sin token | Cualquier `GET /api/...` sin `Authorization` | `401` |
| Cliente inexistente | `POST` o `PUT` con un `rutCliente` que no existe | `404` |
| Horario ocupado | Crear dos veces la misma cita | `201` y luego `409` |
| Cita cancelada libera el horario | `PUT` con `"estadoCita": "Cancelada"` y repetir el `POST` | `200` y luego `201` |
| Servicio caído | `docker compose stop cliente` y crear una cita | `503` |

Para recuperar el servicio: `docker compose start cliente`.

---

## 9. Pruebas unitarias

Cada microservicio tiene pruebas de su capa de servicio en `src/test/java/.../TestService/`, con **JUnit 5**, **Mockito** y **Datafaker**. Se ejecutan con `./mvnw test`.

---

## 10. Control de versiones

El repositorio usa la siguiente estructura de ramas:

| Rama | Uso |
|---|---|
| `main` | Versión estable y entregable |
| `develop` | Integración del trabajo en curso |
| `feature/<nombre>` | Una rama por cambio o funcionalidad (por ejemplo `feature/correcciones-agenda`) |

Los cambios se desarrollan en una rama `feature/`, se integran a `develop` y, cuando están probados, a `main`. Los mensajes de commit son descriptivos, por ejemplo: `fix(agenda): ignorar citas canceladas al validar horario`.

---

## 11. Seguridad

- La wallet de Oracle, el archivo `.env` y las claves **no se versionan** (están en `.gitignore`).
- Las credenciales y el secreto JWT se entregan mediante variables de entorno.
- Los tokens JWT expiran según `JWT_EXPIRATION_MS`; los `DELETE` y el registro de usuarios requieren rol `ADMIN`.

---

## 12. Stack tecnológico

- Java 21
- Spring Boot 3.4.3
- Spring Cloud 2024.0.0 (Eureka Server y Client, Gateway)
- Spring Data JPA (Hibernate)
- Spring Security + JWT (jjwt)
- Oracle Autonomous Database (driver `ojdbc11`)
- springdoc-openapi 2.8.5 (Swagger UI)
- Lombok
- JUnit 5, Mockito, Datafaker y H2 (pruebas)
- Maven con Maven Wrapper
- Docker y Docker Compose
