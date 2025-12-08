# ⚙️ Setup: Cómo correr el proyecto

## 🧩 Requisitos previos

1. Tener **Java 11+** y **Maven** instalados
2. Tener base de datos activa con nombre `volandodb`

---

## 📦 Clonar y compilar

```bash
git clone https://github.com/volando-uy/volando-app.git
cd volando-app/app-central
mvn clean install
```

---

## 🚀 Ejecutar aplicación

```bash
java -jar target/volando-central.jar
```

Esto levanta:

* 🧠 Casos de uso y servicios de negocio
* 🧩 Adaptadores SOAP disponibles en `http://localhost:8086/api`
* 🖥️ GUI Swing embebida (pantalla principal)

---

## ⚙️ Configuración (`application.properties`)

```properties
soap.ip=localhost
soap.port=8086
soap.path=/api
```

Utilizado por `SoapServicePublisher.java`:

```java
String baseUrl = "http://" + config.getIp() + ":" + config.getPort() + config.getPath();
Endpoint.publish(baseUrl + "/userService", new UserSoapAdapter());
```

---

## 🧠 Inicialización

Archivo principal:

```java
public class VolandoApp {
    public static void main(String[] args) {
        DBConnection.init();
        SoapServicePublisher.publishAll();
        new MainFrame(...); // GUI Swing
    }
}
```

---

## ✅ Verificación rápida

* Accedé a `http://localhost:8086/api/UserService?wsdl` para ver si está funcionando
* Abrí la GUI Swing para ver los paneles cargados correctamente

---

Listo, ¡ya estás volando! ✈️
