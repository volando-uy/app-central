package infra.repository.user;

import app.DBConnection;
import domain.models.user.User;
import infra.repository.BaseRepository;
import jakarta.persistence.EntityManager;

public abstract class AbstractUserRepository<T extends User> extends BaseRepository<T> {
    public AbstractUserRepository(Class<T> clazz) {
        super(clazz);
    }

    public boolean existsByEmail(String email) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Long count = em.createQuery(
                            "SELECT COUNT(e) FROM " + getEntityName() + " e WHERE LOWER(e.mail) = :email", Long.class)
                    .setParameter("email", email.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }

    public boolean existsByNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Long count = em.createQuery(
                            "SELECT COUNT(e) FROM " + getEntityName() + " e WHERE LOWER(e.nickname) = :nickname", Long.class)
                    .setParameter("nickname", nickname.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }

    public T findByNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT e FROM " + getEntityName() + " e WHERE LOWER(e.nickname) = :nickname", getEntityClass())
                    .setParameter("nickname", nickname.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public T findByEmail(String email) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT e FROM " + getEntityName() + " e WHERE LOWER(e.mail) = :email", getEntityClass())
                    .setParameter("email", email.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    protected abstract String getEntityName();

    protected abstract Class<T> getEntityClass();
}
