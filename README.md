# ACME Envío de Pedidos - Servicio REST

## Descripción

Este proyecto implementa un servicio REST para el ciclo de abastecimiento de la compañía ACME. El servicio expone una API REST que recibe peticiones en formato JSON, las transforma a XML, las envía a un sistema externo y retorna la respuesta transformada de vuelta a JSON.

## Arquitectura

- **Spring Boot 3.2.0** - Framework principal
- **Java 17** - Versión de Java utilizada
- **Maven** - Gestor de dependencias
- **Docker** - Contenerización

## Estructura del Proyecto

```
src/main/java/com/acme/enviopedidos/
├── controller/
│   └── PedidoController.java          # Controlador REST
├── dto/
│   ├── EnviarPedidoRequest.java      # DTO de solicitud
│   ├── EnviarPedidoWrapperRequest.java
│   ├── EnviarPedidoResponse.java     # DTO de respuesta
│   └── EnviarPedidoWrapperResponse.java
├── service/
│   ├── PedidoService.java            # Servicio principal
│   ├── XmlTransformationService.java # Transformación JSON↔XML
│   └── ExternalApiService.java       # Cliente HTTP externo
└── EnvioPedidosApplication.java      # Clase principal
```

## Endpoints

### POST /acme-envio-pedidos/api/pedidos/enviar

Recibe una solicitud de pedido en formato JSON y retorna la respuesta del sistema de envío.

**Request Body:**
```json
{
    "enviarPedido": {
        "numPedido": "75630275",
        "cantidadPedido": "1",
        "codigoEAN": "00110000765191002104587",
        "nombreProducto": "Armario INVAL",
        "numDocumento": "1113987400",
        "direccion": "CR 72B 45 12 APT 301"
    }
}
```

**Response:**
```json
{
    "enviarPedidoRespuesta": {
        "codigoEnvio": "80375472",
        "estado": "Entregado exitosamente al cliente"
    }
}
```

### GET /acme-envio-pedidos/api/pedidos/health

Endpoint de verificación de salud del servicio.

**Response:**
```
Servicio de envío de pedidos ACME está operativo
```

## Transformación de Datos

### JSON a XML (Request)

| JSON (REST) | XML (SOAP) | Ejemplo |
|-------------|------------|---------|
| numPedido | pedido | 75630275 |
| cantidadPedido | Cantidad | 1 |
| codigoEAN | EAN | 00110000765191002104587 |
| nombreProducto | Producto | Armario INVAL |
| numDocumento | Cedula | 1113987400 |
| direccion | Direccion | CR 72B 45 12 APT 301 |

### XML a JSON (Response)

| XML (SOAP) | JSON (REST) | Ejemplo |
|------------|-------------|---------|
| Codigo | codigoEnvio | 80375472 |
| Mensaje | estado | Entregado exitosamente al cliente |

## Instalación y Ejecución

### Prerrequisitos

- **Java 17 o superior** - Requerido para ejecutar la aplicación
- **Git** - Para clonar el repositorio
- **Docker** (opcional) - Para ejecutar en contenedores

**NOTA:** No necesitas instalar Maven por separado. El proyecto incluye Maven Wrapper que descarga automáticamente las dependencias necesarias.

### Verificación de Prerrequisitos

```bash
# Verificar Java
java -version
# Debe mostrar: java version "17.0.x" o superior

# Verificar Git
git --version
```

### Paso 1: Clonar el Repositorio

```bash
git clone https://github.com/scoutkat/springbooot_back.git
cd springbooot_back
```

### Paso 2: Instalación Automática de Dependencias

El proyecto usa Maven Wrapper que se encarga de descargar todo automáticamente:

**En Windows:**
```bash
# El wrapper descargará Maven 3.9.4 y todas las dependencias
.\mvnw.cmd clean install
```

**En Linux/Mac:**
```bash
# El wrapper descargará Maven 3.9.4 y todas las dependencias
./mvnw clean install
```

**¿Qué hace este comando?**
- ✅ Descarga Maven 3.9.4 automáticamente (si no está instalado)
- ✅ Descarga todas las dependencias del proyecto (Spring Boot, Jackson, etc.)
- ✅ Compila el código fuente
- ✅ Ejecuta las pruebas unitarias
- ✅ Genera el archivo JAR ejecutable

### Paso 3: Ejecutar la Aplicación

**Opción A: Ejecución directa (Recomendado para desarrollo)**

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

**Opción B: Ejecutar el JAR compilado**

```bash
# Después de ejecutar clean install, el JAR estará en target/
java -jar target/envio-pedidos-1.0.0.jar
```

**Opción C: Ejecución con Docker (Recomendado para producción)**

```bash
# Construir imagen Docker
docker build -t acme-envio-pedidos:1.0.0 .

# Ejecutar contenedor
docker run -p 8080:8080 --name acme-pedidos acme-envio-pedidos:1.0.0

# O usando docker-compose (más fácil)
docker-compose up --build
```

### Paso 4: Verificar Instalación

La aplicación estará disponible en:
- **URL Base:** http://localhost:8080/acme-envio-pedidos
- **Health Check:** http://localhost:8080/acme-envio-pedidos/api/pedidos/health

```bash
# Verificar que esté funcionando
curl http://localhost:8080/acme-envio-pedidos/api/pedidos/health
# Respuesta esperada: Servicio de envío de pedidos ACME está operativo
```

### Solución de Problemas Comunes

| Problema | Solución |
|----------|----------|
| `java: command not found` | Instalar Java 17+ desde [Oracle](https://www.oracle.com/java/technologies/downloads/) o [Adoptium](https://adoptium.net/) |
| `mvn: command not found` | No necesitas Maven, usa el wrapper: `.\mvnw.cmd` (Windows) o `./mvnw` (Linux/Mac) |
| `Permission denied` (Linux/Mac) | Dar permisos: `chmod +x mvnw` |
| `Port 8080 already in use` | Cambiar puerto en `application.properties` o cerrar el proceso que usa el puerto |
| `Connection refused` | Asegúrate que la aplicación esté corriendo (ve los logs de inicio) |

### Estructura de Dependencias (Automáticas)

El Maven Wrapper instalará automáticamente:

```xml
<!-- Spring Boot Framework -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.2.0</version>
</dependency>

<!-- Soporte para Web Services (XML/SOAP) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web-services</artifactId>
    <version>3.2.0</version>
</dependency>

<!-- Procesamiento JSON -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- Validación de datos -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**Nota importante:** Todas estas dependencias se descargan automáticamente. No necesitas instalar nada manualmente excepto Java 17+.

## Configuración

Las propiedades de configuración se encuentran en `src/main/resources/application.properties`:

- `server.port`: Puerto del servidor (default: 8080)
- `server.servlet.context-path`: Context path (default: /acme-envio-pedidos)
- `external.api.url`: URL del API externo para envío de pedidos

### Variables de Entorno (Opcional)

Puedes configurar estas variables de entorno para sobreescribir la configuración:

```bash
# En Windows
set SERVER_PORT=8080
set EXTERNAL_API_URL=https://tu-api-externa.com/pedidos

# En Linux/Mac
export SERVER_PORT=8080
export EXTERNAL_API_URL=https://tu-api-externa.com/pedidos
```

## Pruebas con Postman

### Configuración en Postman

#### 1. Health Check (GET)

**Configuración:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/acme-envio-pedidos/api/pedidos/health`
- **Headers:** No se requieren headers especiales
- **Body:** No se requiere body

**Respuesta esperada:**
```
Servicio de envío de pedidos ACME está operativo
```

**Status Code:** `200 OK`

---

#### 2. Enviar Pedido (POST)

**Configuración:**
- **Method:** `POST`
- **URL:** `http://localhost:8080/acme-envio-pedidos/api/pedidos/enviar`

**Headers:**
```
Content-Type: application/json
```

**Body:**
- Seleccionar `raw` y `JSON`
- Copiar y pegar el siguiente JSON:

```json
{
    "enviarPedido": {
        "numPedido": "75630275",
        "cantidadPedido": "1",
        "codigoEAN": "00110000765191002104587",
        "nombreProducto": "Armario INVAL",
        "numDocumento": "1113987400",
        "direccion": "CR 72B 45 12 APT 301"
    }
}
```

**Respuesta esperada:**
```json
{
    "enviarPedidoRespuesta": {
        "codigoEnvio": "80375472",
        "estado": "Entregado exitosamente al cliente"
    }
}
```

**Status Code:** `200 OK`

---

### Pasos Detallados para Postman

1. **Abrir Postman** y crear una nueva request

2. **Para Health Check:**
   - Method: `GET`
   - URL: `http://localhost:8080/acme-envio-pedidos/api/pedidos/health`
   - Click en "Send"

3. **Para Enviar Pedido:**
   - Method: `POST`
   - URL: `http://localhost:8080/acme-envio-pedidos/api/pedidos/enviar`
   - Ir a la pestaña "Headers"
   - Agregar: `Content-Type` = `application/json`
   - Ir a la pestaña "Body"
   - Seleccionar "raw" > "JSON"
   - Pegar el JSON proporcionado arriba
   - Click en "Send"

### Troubleshooting en Postman

| Error | Causa | Solución |
|-------|--------|----------|
| `404 Not Found` | URL incorrecta o aplicación no corriendo | Verificar que la aplicación esté iniciada y la URL sea exacta |
| `405 Method Not Allowed` | Usando GET en lugar de POST | Asegurarse de usar `POST` para el endpoint de envío |
| `400 Bad Request` | JSON mal formateado | Validar sintaxis del JSON body |
| `Connection refused` | Aplicación no iniciada | Iniciar la aplicación con `mvn spring-boot:run` o `docker-compose up` |

### Importar Colección Postman

Para facilitar las pruebas, hemos creado una colección de Postman completa con tests automatizados:

1. **Descargar la colección:** El archivo `postman-collection.json` está en la raíz del proyecto
2. **Importar en Postman:**
   - Abrir Postman
   - Click en "Import"
   - Seleccionar el archivo `postman-collection.json`
   - La colección se importará con 3 peticiones configuradas

**Peticiones incluidas:**
- ✅ **Health Check** - Verifica que el servicio esté operativo
- ✅ **Enviar Pedido** - Prueba con datos válidos
- ✅ **Enviar Pedido - Datos Inválidos** - Prueba el manejo de errores

**Ventajas de usar la colección:**
- Tests automáticos incluidos
- Variables de entorno configuradas
- Respuestas esperadas validadas
- Fácil de ejecutar con un click

O puedes copiar y pegar este JSON directamente al importar:

```json
{
  "info": {
    "name": "ACME Envío Pedidos API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/acme-envio-pedidos/api/pedidos/health",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["acme-envio-pedidos","api","pedidos","health"]
        }
      }
    },
    {
      "name": "Enviar Pedido",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"enviarPedido\": {\n        \"numPedido\": \"75630275\",\n        \"cantidadPedido\": \"1\",\n        \"codigoEAN\": \"00110000765191002104587\",\n        \"nombreProducto\": \"Armario INVAL\",\n        \"numDocumento\": \"1113987400\",\n        \"direccion\": \"CR 72B 45 12 APT 301\"\n    }\n}"
          },
        "url": {
          "raw": "http://localhost:8080/acme-envio-pedidos/api/pedidos/enviar",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["acme-envio-pedidos","api","pedidos","enviar"]
        }
      }
    }
  ]
}
```

## Ejemplos de Uso con cURL

### Enviar Pedido

```bash
curl -X POST http://localhost:8080/acme-envio-pedidos/api/pedidos/enviar \
  -H "Content-Type: application/json" \
  -d '{
    "enviarPedido": {
        "numPedido": "75630275",
        "cantidadPedido": "1",
        "codigoEAN": "00110000765191002104587",
        "nombreProducto": "Armario INVAL",
        "numDocumento": "1113987400",
        "direccion": "CR 72B 45 12 APT 301"
    }
  }'
```

### Verificar Salud

```bash
curl http://localhost:8080/acme-envio-pedidos/api/pedidos/health
```

## Configuración

Las propiedades de configuración se encuentran en `src/main/resources/application.properties`:

- `server.port`: Puerto del servidor (default: 8080)
- `server.servlet.context-path`: Context path (default: /acme-envio-pedidos)
- `external.api.url`: URL del API externo para envío de pedidos

## Pruebas

### Ejecutar Pruebas Unitarias

```bash
mvn test
```

### Ejecutar Pruebas de Integración

```bash
mvn verify
```

## Flujo de Procesamiento

1. **Recepción**: El controlador REST recibe la solicitud JSON
2. **Validación**: Se validan los campos obligatorios
3. **Transformación**: JSON → XML usando `XmlTransformationService`
4. **Envío**: Se envía el XML al API externo via `ExternalApiService`
5. **Procesamiento**: Se recibe la respuesta XML del sistema externo
6. **Transformación**: XML → JSON
7. **Respuesta**: Se retorna la respuesta JSON al cliente

## Tecnologías Utilizadas

- **Spring Boot**: Framework principal para aplicaciones REST
- **Jackson**: Procesamiento JSON
- **RestTemplate**: Cliente HTTP para comunicación externa
- **Jakarta Validation**: Validación de datos de entrada
- **Maven**: Gestión de dependencias y ciclo de vida
- **Docker**: Contenerización de la aplicación

## Consideraciones Técnicas

- El servicio maneja automáticamente la transformación entre JSON y XML
- Incluye validación de campos obligatorios
- Manejo de errores con respuestas apropiadas
- Configurado para CORS para permitir acceso desde diferentes orígenes
- Implementa fallback en caso de fallo del API externo (retorna respuesta mock)

## Autor

Implementado como prueba técnica para ACME - Sistema de Envío de Pedidos
