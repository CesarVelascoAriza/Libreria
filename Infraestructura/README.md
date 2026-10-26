# Módulo de Infraestructura

[← Volver al README principal](../README.md)

## Descripción

La infraestructura del proyecto contiene los servicios transversales que permiten que la aplicación funcione como un ecosistema de microservicios. Aquí se centralizan la observabilidad, la gestión de servicios y la entrada principal a las APIs.

## Componentes principales

### 1. Eureka Server

Ubicación: [Infraestructura/eureka-server](eureka-server)

**Función:**
- registrar todos los microservicios
- permitir el descubrimiento dinámico de servicios
- dar una vista central del estado del sistema

**Diagrama:**

```mermaid
flowchart LR
    S1[Service Books] --> E[Eureka Server]
    S2[Service Users] --> E
    S3[Gateway Service] --> E
    S4[Authorization Server] --> E
    S5[Admin Server] --> E
```

### 2. Gateway Service

Ubicación: [Infraestructura/gateway-service](gateway-service)

**Función:**
- ser el punto único de entrada para clientes
- enrutar peticiones hacia servicios del dominio
- validar JWT y aplicar seguridad del flujo principal

**Diagrama:**

```mermaid
flowchart LR
    C[Cliente] --> G[Gateway Service]
    G --> B[Service Books]
    G --> U[Service Users]
    G --> A[Authorization Server]
```

### 3. Admin Server

Ubicación: [Infraestructura/admin-server](admin-server)

**Función:**
- monitorear servicios y métricas del sistema
- facilitar la administración del ecosistema
- ofrecer una vista central del estado operativo

**Diagrama:**

```mermaid
flowchart LR
    A[Admin Server] --> E[Eureka Server]
    E --> B[Service Books]
    E --> U[Service Users]
    E --> G[Gateway Service]
```

---

## Arquitectura del módulo

```mermaid
flowchart TB
    Client[Cliente] --> GW[Gateway Service]
    GW --> BS[Service Books]
    GW --> US[Service Users]
    GW --> AUTH[Authorization Server]

    BS --> EUREKA[Eureka Server]
    US --> EUREKA
    GW --> EUREKA
    AUTH --> EUREKA

    ADMIN[Admin Server] --> EUREKA
```

---

## Resumen del módulo

Este módulo actúa como la base operacional del proyecto. Sin él, los microservicios no tendrían registro, enrutamiento ni administración centralizada. También soporta la seguridad y la orquestación de rutas entre componentes.
