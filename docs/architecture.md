# 📐 Arquitectura Limpia (Clean Architecture)

El diseño se basa en una separación clara de responsabilidades:

```
┌───────────────────────────────────────────┐
│             Presentation Layer           │ ← GUI Swing + SOAP Adapters
├───────────────────────────────────────────┤
│               Controller Layer            │ ← Interfaces + Implementaciones (IXXXController + XXXController)
├───────────────────────────────────────────┤
│                Service Layer              │ ← Interfaces + Implementaciones (IXXXService + XXXService)
├───────────────────────────────────────────┤
│               Domain Layer (Entities)     │ ← Modelos puros, sin dependencias
├───────────────────────────────────────────┤
│         Infrastructure Layer (JPA)        │ ← Repositorios con persistencia (basados en interfaces)
└───────────────────────────────────────────┘
```

* Todas las capas se **conectan solo mediante contratos (interfaces)**, facilitando testeo, escalabilidad y desacoplamiento.
* Los **controladores y servicios están separados** en interfaces (`IUserService`, `IBookingController`, etc.) e implementaciones reales.
* La capa de repositorio implementa un patrón de repositorio genérico con JPA.

---

## 📁 Estructura de Carpetas

Ruta base: `src/main/java`

```
├── app                      # Bootstrap, config y DB connection
│   ├── DBConnection.java
│   ├── VolandoApp.java
│   └── config/              # Configuración general del sistema
├── adapters                # Adaptadores SOAP + DTOs + Mappers
│   ├── dto/                # Objetos que representan los contratos SOAP
│   ├── mappers/            # Mappers entre entidades y DTOs
│   └── soap/               # Adaptadores expuestos vía Apache CXF
├── controllers             # Interfaces + implementación de controladores
├── domain
│   ├── dtos/               # DTOs internos, distintos de los SOAP
│   ├── models/             # Entidades puras (Airline, Customer, etc.)
│   └── services/           # Interfaces y servicios de negocio
├── infra
│   └── repository/         # Repositorios JPA que implementan interfaces
├── gui                     # Swing UI (con .jfd de Netbeans incluidos)
├── factory                 # Factory de servicios/controladores/repos
└── shared                  # Constantes y utilidades (JWT, imágenes, validaciones)
```

---

## 🖥️ GUI Swing embebida

La app incluye una interfaz Swing para administración local con pantallas como:

* `FlightPanel`
* `GetUserPanel`
* `BookFlightPanel`

Incluye `.jfd` de NetBeans para edición rápida.

---

## 🔌 Adaptadores SOAP (Apache CXF)

El proyecto expone todos los endpoints SOAP mediante adaptadores dedicados por dominio:

* `FlightSoapAdapter.java`
* `UserSoapAdapter.java`
* `BookingSoapAdapter.java`

```text
http://localhost:8086/api/[NombreServicio]  → WSDL autoexpone
```

---

## 🧩 Modularidad & Escalabilidad

* **Fácil de testear:** todo lo importante tiene interfaz
* **Fácil de escalar:** agregás un nuevo dominio siguiendo el patrón actual (`XController`, `XService`, `XRepository`, `XSoapAdapter`)
* **Fácil de migrar:** si se desea pasar a REST, solo se crean nuevos adaptadores sin tocar ni una línea del dominio
