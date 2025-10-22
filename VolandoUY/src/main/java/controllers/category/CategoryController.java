package controllers.category;


import domain.dtos.category.CategoryDTO;
import domain.services.category.ICategoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CategoryController implements ICategoryController {
    private ICategoryService categoryService;

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        return categoryService.createCategory(category);
    }

    @Override
    public CategoryDTO getCategoryDetailsByName(String categoryName) {
        return categoryService.getCategoryDetailsByName(categoryName);
    }

    @Override
    public List<String> getAllCategoriesNames() {
        return categoryService.getAllCategoriesNames();
    }

}
