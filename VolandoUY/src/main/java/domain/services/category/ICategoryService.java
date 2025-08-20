package domain.services.category;



import domain.dtos.category.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    CategoryDTO createCategory(CategoryDTO category);
    List<CategoryDTO> getAllCategories();
    boolean existsCategory(String name);
}
