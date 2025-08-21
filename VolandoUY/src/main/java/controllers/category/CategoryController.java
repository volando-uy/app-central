package controllers.category;


import domain.dtos.category.CategoryDTO;
import domain.services.category.ICategoryService;
import domain.services.flightRoute.IFlightRouteService;
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
    public CategoryDTO getCategoryByName(String categoryName) {
        return categoryService.getCategoryDetailsByName(categoryName);
    }

}
