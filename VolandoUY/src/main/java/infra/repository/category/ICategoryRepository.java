package infra.repository.category;

import domain.models.category.Category;
import infra.repository.IBaseRepository;

public interface ICategoryRepository extends IBaseRepository<Category> {
    Category getCategoryByName(String categoryName);
    boolean existsByName(String categoryName);
}
