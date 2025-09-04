package infra.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import app.DBConnection;

import java.util.List;

public class BaseRepository<T> {

    private final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        EntityManager em = DBConnection.getEntityManager();
        try (em) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
        }
    }
    public T saveOrUpdate(T entity) {
        EntityManager em = DBConnection.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try (em) {
            tx.begin();
            T managed = em.merge(entity);  // 👈 clave: merge, no persist
            tx.commit();
            return managed;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    public T update(T entity) {
        EntityManager em = DBConnection.getEntityManager();
        EntityTransaction tx = null;
        try (em) {
            tx = em.getTransaction();
            tx.begin();
            T mergedEntity = em.merge(entity);
            tx.commit();
            return mergedEntity;
        }
    }

    public T findByKey(Object key) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.find(entityClass, key);
        }
    }

    public Boolean existsByKey(Object key) {
        return findByKey(key) != null;
    }

    public List<T> findAll() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
        }
    }
}
