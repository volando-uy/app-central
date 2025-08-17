# Estructura del proyecto explicada (VolandoUY)

## 📁 Estructura de carpetas

### 📌 app

```
├───src
│   ├───main
│   │   ├───java
│   │   │   ├───app
```

Punto de entrada de la aplicación (`VolandoApp.java`). Acá arranca todo, se inicializan controladores y se lanza la GUI si aplica.

---

### 📌 controllers

```
│   │   │   ├───controllers
│   │   │   │   └───user
```

Capa que expone métodos de alto nivel para manejar los flujos del sistema (como casos de uso). Acá se orquesta entre servicios y validadores.

* Subpaquetes como `user` dividen la responsabilidad por entidad o contexto.
* A futuro podrías tener también `flight`, `paquetes`, `reserva`, etc.

Plantilla típica:

```java
public class UsuarioController implements IUsuarioController {
    private final IUsuarioService usuarioService;
    public UsuarioController(IUsuarioService service) { this.usuarioService = service; }
    public void altaCliente(ClienteDTO dto) { /* lógica */ }
}
```

---

### 📌 domain.dtos

```
│   │   │   ├───domain
│   │   │   │   ├───dtos
│   │   │   │   │   └───user
```

Objetos de transferencia de datos. Son los que viajan desde GUI o Controller hacia los Services.
No tienen lógica, solo datos.

Plantilla:

```java
@Data
public class ClienteDTO extends UsuarioDTO {
    private String apellido;
    // ...
}
```

---

### 📌 domain.models

```
│   │   │   │   ├───models
│   │   │   │   │   └───user
│   │   │   │   │       └───enums
```

Modelos del dominio: representan las entidades reales del negocio (`Cliente`, `Aerolínea`, `Usuario`, etc.). Van con lógica mínima de negocio si aplica.

#### 📌 enums

Enumeraciones relacionadas al modelo, como `EnumTipoDocumento`, `EnumTipoAsiento`, etc.

---

### 📌 services

```
│   │   │   │   └───services
│   │   │   │       └───user
```

Contiene la lógica del negocio. Acá se hacen validaciones de existencia, cálculos, reglas, etc.
No tienen que saber nada de Swing ni de cómo se presentan los datos.

Plantilla:

```java
public class UsuarioService implements IUsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();
    public void altaCliente(Cliente customer) { /* lógica */ }
}
```

---

### 📌 factory

```
│   │   │   ├───factory
```

Contiene clases para instanciar controladores o servicios de forma desacoplada.
Ej: `ControllerFactory` → patrón Singleton o Factory Method.

---

### 📌 gui

```
│   │   │   ├───gui
```

La interfaz gráfica en Swing.
Acá están los `JFrame`, `JPanel`, `JInternalFrame`, los formularios y elementos visuales.

---

### 📌 infra

```
│   │   │   ├───infra
│   │   │   │   ├───config
│   │   │   │   └───repository
```

Todo lo que conecta con servicios externos o tecnologías específicas.

* `config`: para archivos `.properties`, setup de conexión a DB (cuando agregues JPA, va acá).
* `repository`: clases que manejarán la persistencia (aunque por ahora estés en memoria).

---

### 📌 shared

```
│   │   │   └───shared
│   │   │       ├───annotations
│   │   │       ├───constants
│   │   │       └───utils
```

Código común que se comparte entre capas.

* `annotations`: como `@Required`, `@Email`, etc.
* `constants`: mensajes de error, regex, etc.
* `utils`: helpers estáticos, validadores, conversores, etc.

---

### 📌 resources

```
│   │   └───resources
```

Archivos externos, configuraciones (`application.properties`), imágenes, etc.
Usado para internacionalización, logs, etc.

---

### 📌 test

```
│   └───test
│       └───java
```

Tests unitarios o de integración.
Deberías tener el mismo paquete que en `main`, pero dentro de `test`, con clases como `UsuarioServiceTest`.

Plantilla de test:

```java
class UsuarioServiceTest {
    @Test
    void altaCliente_deberiaAgregarCliente() {
        // Arrange, Act, Assert
    }
}
```

---

## 🧠 Sugerencias para escalar:

| Si agregás...             | Podrías crear...                                         |
| ------------------------- | -------------------------------------------------------- |
| Vuelos (`Vuelo`, `Ruta`)  | `controllers.flight`, `services.flight`, `dtos.flight`   |
| Paquetes                  | `controllers.paquete`, `dtos.paquete`, `models.paquete`  |
| Reglas complejas          | `validators`, `rules`, `strategies`                      |
| Tests                     | `test/java/domain/services/user/UsuarioServiceTest.java` |
| Validadores reutilizables | `shared/utils/validators`                                |
| Conversores entre capas   | `shared/utils/mappers` (o integrar ModelMapper)          |

---

## 📦 Dependencias principales (`pom.xml`)

```xml
<dependencies>
    <!-- Lombok para simplificar getters/setters -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.38</version>
        <scope>provided</scope>
    </dependency>

    <!-- PostgreSQL JDBC Driver (para cuando uses persistencia) -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.7.7</version>
    </dependency>

    <!-- ModelMapper para convertir DTOs a Models automáticamente -->
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>5.19.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🔄 ¿Por qué usar ModelMapper?

### Sin ModelMapper:

```java
public Cliente toDomain() {
    Cliente customer = new Cliente();
    customer.setNombre(this.getNombre());
    customer.setApellido(this.getApellido());
    // ...
    return customer;
}
```

### Con ModelMapper:

```java
ModelMapper mapper = new ModelMapper();
Cliente customer = mapper.map(customerDTO, Cliente.class);
```

🟢 Ahorra código repetitivo y mejora mantenibilidad. Ideal cuando los nombres de atributos coinciden.

---

## 🧪 Mockito y JUnit

Mockito y JUnit son tus herramientas clave para testear unidades de lógica en aislamiento:

### JUnit 5

* Define las pruebas unitarias con `@Test`
* Permite validaciones con `assertEquals`, `assertThrows`, `assertTrue`, etc.

### Mockito

* Te permite simular (`mock`) objetos que no querés testear directamente (como servicios, repositorios o controladores).

Ejemplo básico de uso en test:

```java
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {
    @Mock
    IUsuarioService usuarioService;

    @InjectMocks
    UsuarioController userController;

    @Test
    void altaCliente_deberiaLlamarService() {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Test");
        dto.setNickname("testuser");
        dto.setMail("test@test.com");

        userController.altaCliente(dto);

        verify(usuarioService).altaCliente(any(Cliente.class));
    }
}
```

🧠 Este enfoque te permite validar que las responsabilidades estén correctamente delegadas sin depender de implementación real.
