# Módulo de Commons

[← Volver al README principal](../README.md)

## Descripción

El módulo de commons centraliza las entidades, utilidades y servicios genéricos compartidos por los distintos microservicios del proyecto. Su finalidad es evitar duplicación de código y mantener una base común para la capa de negocio.

## Componentes principales

### 1. common-libros

Ubicación: [commons/common-libros](common-libros)

**Función:**
- define la entidad `Book`
- define la entidad `Categoria`
- modela el dominio del libro y su categoría

**Diagrama:**

```mermaid
classDiagram
    class Book {
        +Long isbn
        +String title
        +String subTitle
        +Date datePublication
        +BigDecimal price
        +Categoria categoria
    }

    class Categoria {
        +Long id
        +String description
    }

    Book --> Categoria
```

### 2. commons-usuarios

Ubicación: [commons/commons-usuarios](commons-usuarios)

**Función:**
- define la entidad `Usuario`
- define la entidad `Rol`
- modela la relación entre usuarios y roles

**Diagrama:**

```mermaid
classDiagram
    class Usuario {
        +Long id
        +String userName
        +String password
        +boolean enabled
    }

    class Rol {
        +Long id
        +String nombreRol
    }

    Usuario --> Rol
```

### 3. commons-service

Ubicación: [commons/commons-service](commons-service)

**Función:**
- define el patrón de CRUD base para controladores y servicios
- reutiliza lógica común del dominio
- facilita la extensión de nuevos módulos

**Diagrama:**

```mermaid
classDiagram
    class CommonController~E,S~ {
        +listar()
        +buscarPorId(id)
        +crear(entity)
        +eliminar(id)
    }

    class CommonServiceImp~E,R~ {
        +findAll()
        +findById(id)
        +save(entity)
        +deleteById(id)
    }

    CommonController --> CommonServiceImp
```

---

## Arquitectura del módulo

```mermaid
flowchart TB
    S1[Service Books] --> C1[common-libros]
    S2[Service Users] --> C2[commons-usuarios]
    S3[Todos los servicios] --> C3[commons-service]
```

---

## Resumen del módulo

Este módulo es la base común del proyecto. Proporciona los modelos y la lógica repetitiva que otros servicios usan para construir su funcionalidad sin duplicar código ni romper la coherencia del dominio.
