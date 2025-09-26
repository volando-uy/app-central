package controllers.category;


import domain.dtos.category.CategoryDTO;

import java.util.List;

public interface ICategoryController {
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO getCategoryDetailsByName(String categoryName);
    List<String> getAllCategoriesNames();
}
