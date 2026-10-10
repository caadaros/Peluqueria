# Sistema de Gestión de Peluquería – Microservicios

## Descripción del contexto / dominio del proyecto

Sistema de gestión para una peluquería, desarrollado con una arquitectura de microservicios en Spring Boot, Spring Cloud (Eureka + Gateway) y bases de datos Oracle Autonomous Database y MySQL.

El dominio cubre la administración de clientes, profesionales (peluqueros/as), los tipos de servicio que ofrece la peluquería, la disponibilidad horaria de cada profesional y la agenda de citas, que conecta a un cliente con un profesional, un servicio y un horario disponible. Además incluye autenticación con JWT y módulos de inventario y ventas (productos, bodegas, kardex, boletas, pagos y notificaciones).

Cada entidad del dominio es un microservicio independiente, registrado en Eureka y expuesto a través de un API Gateway único.

---

## Nombre del estudiante
- Daniel Azocar
- Carolina Adaros

---

## Microservicios implementados

| Microservicio | Descripción | Puerto local | Base de datos |
|---|---|---|---|
| **eureka** | Servidor de descubrimiento (Service Registry) | 8761 | – |
| **gateway** | API Gateway, punto de entrada único | 8080 | – |
| **tipoServicio** | Catálogo de servicios ofrecidos (corte, color, manicure, etc.) | 8081 | MySQL |
| **profesional** | Gestión de profesionales y sus especialidades | 8082 | MySQL |
| **disponibilidadProfesional** | Disponibilidad horaria de cada profesional | 8083 | MySQL |
| **cliente** | Gestión de clientes | 8084 | Oracle |
| **agenda** | Citas; consulta a cliente, tipoServicio y disponibilidad | 8085 | MySQL |
| **auth** | Login y registro de usuarios, emisión de JWT | 8086 | Oracle |
| **registro** | Registro de atenciones (cita, cliente, profesional, servicio y producto usado) | 8087 | MySQL |
| **producto** | Catálogo de productos | 8088 | MySQL |
| **bodega** | Bodegas | 8089 | MySQL |
| **kardex** | Movimientos y stock; consulta a producto y bodega | 8090 | MySQL |
| **boleta** | Boletas | 8091 | MySQL |
| **pago** | Pagos | 8092 | MySQL |
| **notificacion** | Notificaciones a clientes | 8093 | MySQL |

---

## Rutas principales del Gateway

Las rutas se exponen a través del Gateway (puerto 8080). El Gateway enruta con balanceo de carga (`lb://`) hacia el microservicio registrado en Eureka. Todas las rutas `/api/**` exigen un token JWT (`Authorization: Bearer <token>`), y los `DELETE` exigen rol ADMIN.

| Ruta del Gateway | Microservicio destino |
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

### Ejemplo de uso (local)

```
POST http://localhost:8080/auth/login          (body: {"username":"...","password":"..."})
GET  http://localhost:8080/api/cliente         (header: Authorization: Bearer <token>)
POST http://localhost:8080/api/cliente
GET  http://localhost:8080/api/tiposervicio
GET  http://localhost:8080/api/agenda
```

---

## Documentación Swagger

### Local (Docker Compose)

| Microservicio | Swagger UI local |
|---|---|
| Cliente | http://localhost:8084/swagger-ui.html |
| Profesional | http://localhost:8082/swagger-ui.html |
| Tipo de Servicio | http://localhost:8081/swagger-ui.html |
| Disponibilidad Profesional | http://localhost:8083/swagger-ui.html |
| Agenda | http://localhost:8085/swagger-ui.html |
| Auth | http://localhost:8086/swagger-ui.html |
| Eureka (dashboard) | http://localhost:8761 |

Los demás servicios siguen el mismo patrón: `http://localhost:<puerto>/swagger-ui.html`.

### Remota (desplegada en Render)

| Microservicio | Swagger UI |
|---|---|
| Gateway (vista general) | https://gateway-dtzp.onrender.com/swagger-ui.html |
| Cliente | https://gateway-dtzp.onrender.com/cliente/v3/api-docs |
| Profesional | https://gateway-dtzp.onrender.com/profesional/v3/api-docs |
| Tipo de Servicio | https://gateway-dtzp.onrender.com/tipoServicio/v3/api-docs |
| Disponibilidad Profesional | https://gateway-dtzp.onrender.com/disponibilidadProfesional/v3/api-docs |
| Agenda | https://gateway-dtzp.onrender.com/agenda/v3/api-docs |

---

## Instrucciones de ejecución

### Requisitos previos

- Git
- Docker y Docker Compose (para la ejecución con contenedores)
- JDK 21 (solo si compilas con Maven fuera de Docker; el proyecto incluye Maven Wrapper `mvnw`, no hace falta instalar Maven)
- Acceso a la base de datos Oracle (carpeta `Wallet_…`, incluida en el repositorio) y a una base MySQL

### 1. Clonar el repositorio

```bash
git clone <URL-DEL-REPOSITORIO>
cd <CARPETA-DEL-REPOSITORIO>
```

### 2. Configurar las variables de entorno

```bash
cp .env.example .env
```

Edita `.env` y completa los valores reales (secreto JWT, usuario administrador, credenciales de Oracle y de MySQL). El archivo `.env` no se sube a Git.

Para generar un secreto JWT: `openssl rand -base64 32`.

### 3A. Ejecución con Docker Compose (recomendada)

```bash
docker compose up --build
```

Docker construye una imagen por microservicio (las dependencias se descargan dentro del contenedor, no hay que instalarlas a mano). Luego:

1. Verifica Eureka: http://localhost:8761
2. Prueba el Gateway: `POST http://localhost:8080/auth/login`, y con el token `GET http://localhost:8080/api/cliente`

Para detener el sistema:

```bash
docker compose down
```

Para reconstruir un solo microservicio tras un cambio de código:

```bash
docker compose up --build cliente
```

### 3B. Compilar y empaquetar con Maven (genera el .jar)

Cada microservicio es un proyecto Maven independiente. Ejemplo con `cliente`:

```bash
cd cliente
./mvnw clean                       # borra la carpeta target
./mvnw package -DskipTests         # compila y genera target/app.jar
./mvnw install -DskipTests         # además copia el jar al repositorio local (~/.m2)
```

En Windows usa `mvnw.cmd` en lugar de `./mvnw`. Para ejecutar las pruebas unitarias, omite `-DskipTests`.

El resultado es `target/app.jar` (el nombre es fijo por `<finalName>app</finalName>` en el `pom.xml`). Para ejecutarlo fuera de Docker:

```bash
java -jar target/app.jar
```

Requiere que Eureka esté disponible (`EUREKA_URL=http://localhost:8761/eureka/`), el secreto `JWT_SECRET`, las variables de base de datos del servicio y, en `cliente` y `auth`, que `ORACLE_URL` apunte a la ruta local del wallet.

> Nota: los módulos `auth`, `producto`, `bodega`, `kardex`, `boleta`, `pago`, `registro` y `notificacion` no incluyen `mvnw`; en ellos usa `mvn` instalado, o copia la carpeta `.mvn` y los archivos `mvnw`/`mvnw.cmd` desde otro módulo.

### Ejecución remota (Render)

Los microservicios están desplegados en Render como Web Services independientes, construidos desde su `Dockerfile`. Orden de despliegue: `eureka` → `cliente`, `profesional`, `tipoServicio`, `disponibilidadProfesional` → `agenda` → `gateway`.

Punto de entrada público: `https://gateway-dtzp.onrender.com`

---

## Stack tecnológico

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring Cloud 2024.0.0** (Eureka Server/Client, Gateway)
- **Spring Data JPA** (Hibernate)
- **Spring Security + JWT** (jjwt 0.13.0)
- **Oracle Autonomous Database** (driver `ojdbc11`) y **MySQL**
- **springdoc-openapi 2.8.5** (Swagger UI)
- **Lombok**
- **JUnit 5 + Mockito + Datafaker + H2** (pruebas unitarias)
- **Maven** (con Maven Wrapper)
- **Docker / Docker Compose**
- **Render** (despliegue)
