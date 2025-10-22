package infra.repository.category;

import app.DBConnection;
import domain.models.category.Category;
import jakarta.persistence.EntityManager;

public class CategoryRepository extends AbstractCategoryRepository implements ICategoryRepository {
    @Override
    public Category getCategoryByName(String categoryName) {
        try (EntityManager em= DBConnection.getEntityManager()) {
            return em.createQuery("SELECT c FROM Category c WHERE LOWER(c.name)=:name", Category.class)
                    .setParameter("name", categoryName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public boolean existsByName(String categoryName){
        try (EntityManager em= DBConnection.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(c) FROM Category c WHERE LOWER(c.name)=:name", Long.class)
                    .setParameter("name", categoryName.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }
}
