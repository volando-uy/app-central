# 🧪 Testing y Casos de Uso

El sistema se testea a dos niveles: flujo completo y pruebas unitarias.

---

## 🎯 1. Casos de Uso End-to-End Simulados

Ubicados en `test/java/casosdeuso/` prueban flujos reales del dominio, como:

* Registro de usuarios (Customer, Airline)
* Booking de vuelos
* Emisión de tickets
* Creación de rutas y vuelos

> Simulan la ejecución completa, inyectando dependencias reales o mockeadas.

---

## 🧪 2. Unit Testing por capa

### Controllers

Testean la lógica expuesta y validaciones:

```java
@Test
void shouldReturnErrorWhenUserIsNull() {
    // test de controller
}
```

### Services

Aseguran la lógica de negocio sin efectos externos:

```java
@Test
void shouldBookFlightWithValidData() {
    // test unitario del servicio
}
```

### Shared utils (JWT, validaciones, etc.)

Testean helpers puros y validaciones con JavaX Validator

---

## 🧩 Organización

```
└── test/java
    ├── casosdeuso/          # Tests E2E simulados
    ├── controllers/         # Tests unitarios de controladores
    ├── services/            # Tests unitarios de servicios
    └── shared/utils/        # Helpers y validaciones
```

---

## 🧵 Tip

Los casos de uso pueden correr sin necesidad de levantar GUI ni SOAP.
Usan `Factory` para componer dependencias y correr en modo test.
