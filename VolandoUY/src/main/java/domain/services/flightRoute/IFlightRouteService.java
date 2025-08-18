package domain.services.flightRoute;

import domain.dtos.flightRoute.CategoryDTO;

import java.util.List;

public interface IFlightRouteService {
    CategoryDTO createCategory(CategoryDTO category);
    List<CategoryDTO> getAllCategories();
}
