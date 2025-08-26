package infra.repository.category;

import domain.models.category.Category;

public interface ICategoryRepository {
    Category getCategoryByName(String categoryName);
    boolean existsByName(String categoryName);
}
