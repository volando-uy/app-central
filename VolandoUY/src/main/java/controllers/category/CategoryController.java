package controllers.category;


import domain.dtos.category.CategoryDTO;
import domain.services.category.ICategoryService;
import domain.services.flightRoute.IFlightRouteService;

import java.util.List;

public class CategoryController implements ICategoryController {
    private ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        return categoryService.createCategory(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
