# Módulo de Seguridad

[← Volver al README principal](../README.md)

## Descripción

La capa de seguridad del proyecto gestiona la autenticación, autorización y validación de usuarios mediante OAuth2 y JWT. Esta capa es esencial para proteger los endpoints y controlar el acceso a los servicios del sistema.

## Componentes principales

### 1. Authorization Server

Ubicación: [Seguridad/seguridad](seguridad)

**Función:**
- emitición de tokens JWT
- autenticación de usuarios
- registro de clientes OAuth2
- validación de permisos y roles

**Diagrama funcional:**

```mermaid
sequenceDiagram
    participant C as Cliente
    participant G as Gateway
    participant A as Authorization Server
    participant U as UserServiceImp
    participant DB as MySQL

    C->>G: Solicita recurso protegido
    G->>A: Redirección a autorización
    A->>U: loadUserByUsername(username)
    U->>DB: Consulta usuario y roles
    DB-->>U: Datos del usuario
    U-->>A: UserDetails
    A-->>C: JWT emitido
    C->>G: Request con Bearer Token
    G->>A: Validar token
    A-->>G: Token válido
    G-->>C: Respuesta autorizada
```

### 2. Modulo adicional de seguridad

Ubicación: [Seguridad/Autorization-server](Autorization-server)

**Función:**
- complementar la configuración de seguridad del proyecto
- apoyar la integración con OpenFeign y validación de identidad
- mantener una segunda implementación o alternativa de autorización

**Diagrama:**

```mermaid
flowchart LR
    U[Usuario] --> AS[Authorization Server]
    AS --> US[UserService]
    US --> DB[(MySQL)]
    AS --> G[Gateway]
```

---

## Arquitectura del módulo

```mermaid
flowchart TB
    G[Gateway] --> A[Authorization Server]
    A --> U[UserServiceImp]
    U --> DB[(MySQL)]
    A --> JWT[JWT / OAuth2]
    JWT --> G
```

---

## Resumen del módulo

La seguridad está diseñada para brindar control centralizado de acceso al ecosistema. A través del Authorization Server, los usuarios son autenticados y reciben tokens que permiten acceder a los recursos protegidos a través del Gateway.
