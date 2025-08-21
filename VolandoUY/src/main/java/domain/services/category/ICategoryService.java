package domain.services.category;



import domain.dtos.category.CategoryDTO;
import domain.models.category.Category;

import java.util.List;

public interface ICategoryService {
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO getCategoryDetailsByName(String categoryName);
    Category getCategoryByName(String categoryName);
    boolean existsCategory(String categoryName);
    List<String> getAllCategoriesNames();
}
