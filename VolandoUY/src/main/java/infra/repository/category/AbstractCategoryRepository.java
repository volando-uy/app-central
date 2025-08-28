package infra.repository.category;

import domain.models.category.Category;
import infra.repository.BaseRepository;

public abstract class AbstractCategoryRepository extends BaseRepository<Category> {
    public AbstractCategoryRepository() {
        super(Category.class);
    }
}
