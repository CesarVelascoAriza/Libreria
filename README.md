# Libreria - Documentación general del proyecto

## Resumen general

Libreria es un proyecto de backend basado en microservicios desarrollado con Java y Spring Boot. Su objetivo es gestionar una librería digital mediante servicios independientes que se coordinan a través de infraestructura común, seguridad y una base de datos relacional.

El sistema está dividido en cuatro grandes bloques:

- Infraestructura: descubrimiento, gateway y administración
- Dominio de negocio: servicios de libros y usuarios
- Seguridad: autenticación y autorización con OAuth2 / JWT
- Commons: entidades y servicios reutilizables

Este enfoque permite separar responsabilidades, facilitar la escalabilidad y mantener un diseño modular para futuras extensiones del sistema.

---

## Arquitectura general

```mermaid
flowchart LR
    U[Cliente / Usuario] --> G[Gateway Service]
    G --> BS[Service Books]
    G --> US[Service Users]
    G --> AS[Authorization Server]
    BS --> DB[(MySQL)]
    US --> DB
    AS --> DB

    E[Eureka Server] --> BS
    E --> US
    E --> G
    E --> AS
    A[Admin Server] --> E
```

### Descripción de la arquitectura

- El cliente accede al sistema mediante un único punto de entrada: el Gateway.
- El Gateway distribuye las solicitudes hacia los microservicios del negocio.
- Eureka registra y descubre cada servicio del ecosistema.
- El Authorization Server genera y valida tokens JWT para proteger los endpoints.
- Los servicios del dominio acceden a la base de datos MySQL para persistir la información.
- El Admin Server permite monitorear y administrar la infraestructura.

---

## Inventario de módulos

| Módulo | Ruta | Función principal | Componentes clave |
|---|---|---|---|
| Infraestructura | [Infraestructura](Infraestructura) | Descubrimiento, gateway y administración | Eureka, Gateway, Admin Server |
| DominioNegocio | [DominioNegocio](DominioNegocio) | Lógica funcional del negocio | service-books, service-users, service-factura |
| Seguridad | [Seguridad](Seguridad) | Autenticación y autorización | Authorization Server, OAuth2, JWT |
| Commons | [commons](commons) | Entidades y servicios compartidos | common-libros, commons-usuarios, commons-service |

---

## Documentación por módulo

### 1. Infraestructura

Responsable de la base operativa del sistema.

- Registro y descubrimiento de servicios con Eureka
- Entrada centralizada del sistema con Gateway
- Administración y monitoreo con Admin Server
- Despliegue con Docker Compose

Ir a: [Infraestructura/README.md](Infraestructura/README.md)

### 2. Dominio de negocio

Contiene la lógica funcional relacionada con la librería y gestión del negocio.

- libros y categorías
- usuarios y roles
- servicios REST del dominio
- procesos transversales del negocio

Ir a: [DominioNegocio/README.md](DominioNegocio/README.md)

### 3. Seguridad

Encargado de proteger la aplicación y controlar los accesos.

- autenticación de usuarios
- emisión de tokens JWT
- validación de tokens en el Gateway
- control de roles y permisos

Ir a: [Seguridad/README.md](Seguridad/README.md)

### 4. Commons

Repositorio compartido con entidades y utilidades comunes.

- entidades de dominio reutilizables
- lógica base para servicios y controladores
- reducción de duplicación de código

Ir a: [commons/README.md](commons/README.md)

---

## Diagrama general del contexto

```mermaid
flowchart TB
    subgraph Cliente
        U[Usuario / Sistema externo]
    end

    subgraph Aplicacion
        G[Gateway Service]
        E[Eureka Server]
        A[Admin Server]
        B[Service Books]
        C[Service Users]
        S[Authorization Server]
        DB[(MySQL)]
    end

    U --> G
    G --> B
    G --> C
    G --> S

    B --> E
    C --> E
    S --> E
    G --> E
    A --> E

    B --> DB
    C --> DB
    S --> DB
```

---

## Diagrama general de componentes

```mermaid
flowchart TB
    subgraph Cliente
        U[Cliente / Usuario]
    end

    subgraph Entrada
        G[Gateway Service]
    end

    subgraph Negocio
        B[Service Books]
        U2[Service Users]
        F[Service Factura]
    end

    subgraph Seguridad
        S[Authorization Server]
        JWT[JWT / OAuth2]
    end

    subgraph Infraestructura
        E[Eureka Server]
        A[Admin Server]
    end

    subgraph Datos
        DB[(MySQL)]
    end

    U --> G
    G --> B
    G --> U2
    G --> F
    G --> S
    S --> JWT
    JWT --> G

    B --> E
    U2 --> E
    F --> E
    S --> E
    G --> E
    A --> E

    B --> DB
    U2 --> DB
    S --> DB
```

---

## Diagrama general de clases

```mermaid
classDiagram
    class CommonController~E,S~ {
        +listar()
        +listar(Pageable)
        +buscarPorId(Long)
        +crear(E, BindingResult)
        +eliminar(Long)
    }

    class CommonService~E~ {
        <<interface>>
        +findAll()
        +findAll(Pageable)
        +findById(Long)
        +save(E)
        +deleteById(Long)
    }

    class CommonServiceImp~E,R~ {
        +repository
        +findAll()
        +findAll(Pageable)
        +findById(Long)
        +save(E)
        +deleteById(Long)
    }

    class BookController {
        +updateBook(Long, Book)
    }

    class UserController {
        +updateUser(Long, Usuario)
        +getUserName(String)
    }

    class BookServiceImp {
    }

    class UserServiceImp {
        +loadUserByUsername(String)
    }

    class Book {
        -Long isbn
        -String title
        -String subTitle
        -Date datePublication
        -BigDecimal price
        -Categoria categoria
        -Date createAt
        -byte[] image
    }

    class Categoria {
        -Long id
        -String description
    }

    class Usuario {
        -Long id
        -String userName
        -String password
        -boolean enabled
    }

    class Rol {
        -Long id
        -String nombreRol
    }

    CommonController --> CommonService
    CommonServiceImp ..|> CommonService
    BookController --|> CommonController
    UserController --|> CommonController
    BookServiceImp --|> CommonServiceImp
    UserServiceImp ..|> UserService
    Book --> Categoria
    Usuario --> Rol
```

---

## Diagrama general de base de datos

```mermaid
erDiagram
    CATEGORIAS ||--o{ LIBROS : contiene
    USUARIOS ||--o{ USUARIO_ROLES : tiene
    ROLES ||--o{ USUARIO_ROLES : asigna

    CATEGORIAS {
        bigint id PK
        varchar description
    }

    LIBROS {
        bigint isbn PK
        varchar title
        varchar sub_title
        date date_publication
        decimal price
        bigint categoria_id FK
        datetime create_at
        longblob image
    }

    USUARIOS {
        bigint id PK
        varchar user_name
        varchar password
        boolean enabled
    }

    ROLES {
        bigint id PK
        varchar nombre_rol
    }

    USUARIO_ROLES {
        bigint usuario_id PK, FK
        bigint role_id PK, FK
    }
```

---

## Tecnologías principales

- Java 21
- Spring Boot 4
- Spring Cloud
- Spring Security
- OAuth2 Authorization Server
- Eureka
- Spring Cloud Gateway
- JPA / Hibernate
- MySQL
- Docker Compose

---

## Estado del proyecto

El proyecto presenta una estructura modular clara y una separación adecuada de responsabilidades. La solución está preparada para crecer, agregar nuevos servicios y fortalecer la seguridad en la capa de acceso y negocio.

Este README representa la documentación general del repositorio. La información detallada de cada bloque queda en los READMEs específicos de cada módulo.
