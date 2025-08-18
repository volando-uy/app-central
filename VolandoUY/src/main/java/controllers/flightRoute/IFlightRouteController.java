package controllers.flightRoute;

import domain.dtos.flightRoute.CategoryDTO;

import java.util.List;

public interface IFlightRouteController {
    CategoryDTO createCategory(CategoryDTO category);
    List<CategoryDTO> getAllCategories();
}
