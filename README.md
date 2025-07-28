# University Microservices Project

## Descripción
Proyecto de arquitectura de microservicios para gestión universitaria implementado con Spring Boot, FastAPI y base de datos H2.

## Arquitectura
- **core-service**: Microservicio principal (Spring Boot) - Puerto 8080
- **spring-client**: Cliente Spring Boot - Puerto 8081
- **fastapi-client**: Cliente FastAPI - Puerto 8000

## Entidades
- Customer (Clientes)
- Employee (Empleados)
- Product (Productos)
- Supplier (Proveedores)
- PurchaseOrder (Órdenes de Compra)
- OrderProduct (Productos por Orden)

## Funcionalidades
- ✅ CRUD completo para todas las entidades
- ✅ Autenticación de empleados
- ✅ Control de stock
- ✅ APIs REST
- ✅ Base de datos H2 en memoria

## Cómo ejecutar

### Core Service
```bash
cd core-service
mvn spring-boot:run
```

### Spring Client
```bash
cd spring-client
mvn spring-boot:run
```

### FastAPI Client
```bash
cd fastapi-client
pip install -r requirements.txt
uvicorn main:app --reload
```

## Endpoints principales
- GET/POST/PUT/DELETE `/api/customers`
- GET/POST/PUT/DELETE `/api/products`
- GET/POST/PUT/DELETE `/api/suppliers`
- GET/POST/PUT/DELETE `/api/orders`
- POST `/api/employees/authenticate`

## Tecnologías
- Java 17
- Spring Boot 3.x
- Python 3.x
- FastAPI
- H2 Database
- Maven
- JPA/Hibernate