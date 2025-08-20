package controllers.category;


import domain.dtos.category.CategoryDTO;

import java.util.List;

public interface ICategoryController {
    CategoryDTO createCategory(CategoryDTO category);
    List<CategoryDTO> getAllCategories();
}
