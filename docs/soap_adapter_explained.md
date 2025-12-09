# 🧼 BaseSoapAdapter: Publicador Manual de Servicios SOAP con Apache CXF 4.1.4

Este adaptador abstracto permite publicar servicios SOAP **sin usar `Endpoint.publish(...)`**, debido a una incompatibilidad interna con la generación de wrappers en CXF 4.1.4. A continuación se explica **paso a paso** cada parte del código, y por qué fue necesario hacerlo así.

---

## 😵‍💫 ¿Por qué no `Endpoint.publish(...)`?

En versiones recientes de CXF (como 4.1.4), el método `Endpoint.publish` puede fallar con:

```
NullPointerException at WrapperClassGenerator
```

Esto se debe a que el **bus por defecto no tiene el `WrapperClassCreator` ni el `WSDLManager` registrados**, lo cual rompe la generación dinámica de clases para WSDLs con wrappers.

> 🔥 CXF 4+ se volvió más "manual": necesitas registrar todas las extensiones vos mismo si no usás un runtime servlet.

Solución: crear manualmente el `Bus`, registrar las extensiones necesarias y publicar usando `JaxWsServerFactoryBean`.

---

## 🧠 ¿Qué es cada componente?

### 🚌 `Bus`

Es el **núcleo de CXF**, un contenedor de componentes y extensiones (como el `ApplicationContext` de Spring). Sirve para:

* Gestionar bindings, transportes, WSDLs, etc.
* Reemplazar y configurar el comportamiento de CXF sin usar `web.xml` o contenedores servlet.

> En este caso: **Creamos nuestro `Bus` manualmente** y le registramos todo lo necesario.

### ⚙️ `WrapperClassGenerator`

Se encarga de generar dinámicamente clases wrapper para métodos con múltiples parámetros en servicios SOAP.

* Necesario para generar WSDLs que definan correctamente las operaciones.
* Si no lo registrás → `NullPointerException`

### 📄 `WSDLManagerImpl`

Gestor de descripciones WSDL en CXF.

* Sin esto, el endpoint no puede generar el `?wsdl`.
* Por defecto está en el `Bus`, pero en entornos manuales no se inicializa.

### 🧼 `SoapBindingFactory`

Define **cómo se serializa y deserializa** el contenido SOAP.

* Necesario para que CXF entienda el protocolo SOAP 1.1/1.2.
* Se registra manualmente con el `BindingFactoryManager` del `Bus`.

### 🔌 `JettyHTTPServerEngineFactory`

Responsable de **exponer los servicios directamente sobre Jetty**, sin necesidad de Tomcat, Servlet o contenedor externo.

```java
JettyHTTPServerEngineFactory jettyFactory = bus.getExtension(JettyHTTPServerEngineFactory.class);
if (jettyFactory == null) {
    jettyFactory = new JettyHTTPServerEngineFactory();
    jettyFactory.setBus(bus);
    bus.setExtension(jettyFactory, JettyHTTPServerEngineFactory.class);
}
```

#### 🔍 ¿Qué pasa acá?

* Verificamos si Jetty ya está configurado para este `Bus`.
* Si no lo está, lo instanciamos y lo registramos.
* Esto es **clave**: si Jetty no está disponible, **el endpoint no se publica**.

> ✅ Jetty es un servidor HTTP embebido, ideal para levantar CXF sin un contenedor JavaEE.

### 🏗️ `JaxWsServerFactoryBean`

Es una **fábrica avanzada de endpoints SOAP** en CXF.

* Permite crear servicios configurando todo a mano (bus, binding, instancia, etc.).
* Es el reemplazo profesional y flexible de `Endpoint.publish()`.

```java
JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
factory.setBus(bus);
factory.setServiceClass(this.getClass());
factory.setServiceBean(this);
factory.setAddress(getEndpointUrl());
factory.setBindingId("http://schemas.xmlsoap.org/wsdl/soap/");
factory.create();
```

Esto hace que tu servicio quede:

* 📌 Publicado en la URL indicada
* 🔗 Registrado en Jetty
* 🧠 Expuesto con su WSDL generado correctamente

---

## 📄 Explicación del flujo `publish()`

```java
public void publish() {
    // ...
    Bus bus = BusFactory.newInstance().createBus();
    BusFactory.setDefaultBus(bus);
    bus.setExtension(new WrapperClassGenerator(bus), WrapperClassCreator.class);
    bus.setExtension(new WSDLManagerImpl(), WSDLManager.class);
    
    SoapBindingFactory sbf = new SoapBindingFactory();
    sbf.setBus(bus);
    bus.getExtension(BindingFactoryManager.class)
       .registerBindingFactory("http://schemas.xmlsoap.org/wsdl/soap/", sbf);

    JettyHTTPServerEngineFactory jettyFactory = bus.getExtension(JettyHTTPServerEngineFactory.class);
    if (jettyFactory == null) {
        jettyFactory = new JettyHTTPServerEngineFactory();
        jettyFactory.setBus(bus);
        bus.setExtension(jettyFactory, JettyHTTPServerEngineFactory.class);
    }

    JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
    factory.setBus(bus);
    factory.setServiceClass(this.getClass());
    factory.setServiceBean(this);
    factory.setAddress(getEndpointUrl());
    factory.setBindingId("http://schemas.xmlsoap.org/wsdl/soap/");
    factory.create();

    System.out.printf("Servicio SOAP %s publicado en: %s?wsdl%n", getServiceName(), getEndpointUrl());
}
```

---

## ✅ Resultado

Tu servicio queda disponible en:

```
http://localhost:8086/api/UserService?wsdl
```

Y no rompe con `NullPointerException`, porque:

* El `Bus` es creado manualmente
* Se registran todas las extensiones críticas
* Jetty se levanta como engine HTTP embebido

---

## 🧵 Conclusión

Si estás usando **Apache CXF 4.1.4+**, olvidate de `Endpoint.publish()` para servicios con wrappers o múltiples parámetros. Este patrón de `BaseSoapAdapter` te da:

* Control total sobre el entorno de publicación
* Compatibilidad con WSDLs complejos
* Aislamiento de configuración

> Extendé con facilidad:

```java
public class UserSoapAdapter extends BaseSoapAdapter implements IUserServiceSoap {
    @Override
    protected String getServiceName() {
        return "UserService";
    }

    // Métodos SOAP
}
```

Y solo llamás:

```java
new UserSoapAdapter().publish();
```

Y sale andando 🚀
