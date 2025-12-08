# 🧠 Mappers & DTOs: Separación SOAP vs Interno

El sistema separa claramente los modelos internos (entidades de dominio) de los DTOs expuestos en la capa SOAP.

---

## 🧪 ¿Por qué separarlos?

* Evitás contaminar el dominio con frameworks (`@XmlRootElement`, `@JsonProperty`, etc.)
* Mantenés independencia y limpieza
* Permitís evolucionar ambos modelos de forma aislada

---

## 🧰 Mapeo con `UserSoapMapper`

```java
public class UserSoapMapper {
    public static SoapUserDTO toSoap(User user) {
        // Mapea de entidad a DTO SOAP
    }

    public static User fromSoap(SoapUserDTO dto) {
        // Mapea de DTO SOAP a entidad
    }
}
```

---

## ✨ Buenas prácticas aplicadas

* 📦 Los mappers viven en `adapters/mappers`
* 🚫 No se usa MapStruct ni frameworks mágicos
* 🔄 El mapeo es explícito, controlado y testeable

---

## 🧵 Tip

Los DTOs para SOAP viven en `adapters/dto`, separados de los `domain/dtos` usados internamente.

Así mantenés:

* Dominio puro sin acoplamientos
* Adaptadores SOAP limpios y enfocados solo en transporte
* Mapeo controlado y extensible
