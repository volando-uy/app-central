# 🧬 Repositorios Limpios con Interfaces

## Repositorio Base Genérico

Todos los repositorios se abstraen vía `IBaseRepository<T>`:

```java
public interface IBaseRepository<T> {
    void save(T entity);
    T saveOrUpdate(T entity);
    T update(T entity);
    T findByKey(Object key);
    Boolean existsByKey(Object key);
    List<T> findAll();
}
```

Implementación base compartida:

```java
public abstract class BaseRepository<T> implements IBaseRepository<T> {
    private final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    // Métodos comunes reutilizables van acá
}
```

---

## 👥 Repositorios de Usuario: Extensibilidad sin dolor

El dominio de usuarios está modelado de forma jerárquica y flexible:

### 1. `IUserRepository`

Métodos para `User` como entidad abstracta:

```java
User getUserByEmail(String email);
User getUserByNickname(String nickname, boolean full);
```

### 2. `IAbstractUserRepository<T extends User>`

Nivel genérico para subtipos:

```java
boolean existsByEmail(String email);
boolean existsByNickname(String nickname);
T findByNickname(String nickname);
T findByEmail(String email);
```

Implementado por:

```java
public abstract class AbstractUserRepository<T extends User>
        extends BaseRepository<T>
        implements IAbstractUserRepository<T> {

    public AbstractUserRepository(Class<T> clazz) {
        super(clazz);
    }
}
```

### 3. `ICustomerRepository`

Operaciones específicas de `Customer`:

```java
Customer getCustomerByNickname(String nickname);
Customer getCustomerByEmail(String email);
List<Customer> findFullAll();
Customer findFullByNickname(String nickname);
```

Implementado por:

```java
public class CustomerRepository extends AbstractUserRepository<Customer>
        implements ICustomerRepository {

    public CustomerRepository() {
        super(Customer.class);
    }
}
```

### 🧠 Beneficios

* Extensión limpia y escalable
* Lógica común heredada
* Fácil de testear y mockear
* Separación de responsabilidades

### 🧵 Caso de uso real

```java
public class GetCustomerByEmailUseCase {
  private final ICustomerRepository repository;

  public GetCustomerByEmailUseCase(ICustomerRepository repository) {
    this.repository = repository;
  }

  public Customer execute(String email) {
    return repository.getCustomerByEmail(email);
  }
}
```

---

## ☝️ Tip: Organización por tipo

Si `User` es abstracta:

* `CustomerRepository`
* `AirlineRepository`
* `UserRepository` actúa como façade combinada
