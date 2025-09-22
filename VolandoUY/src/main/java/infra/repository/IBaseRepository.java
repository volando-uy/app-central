package infra.repository;


import java.util.List;

public interface IBaseRepository<T> {
    void save(T entity);
    T saveOrUpdate(T entity);
    T update(T entity);
    T findByKey(Object key);
    Boolean existsByKey(Object key);
    List<T> findAll();
}