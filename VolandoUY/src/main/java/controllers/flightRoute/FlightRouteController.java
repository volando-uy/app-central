package controllers.flightRoute;

import domain.dtos.flightRoute.CategoryDTO;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FlightRouteController implements IFlightRouteController{
    private IFlightRouteService flightRouteService;

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        return flightRouteService.createCategory(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return flightRouteService.getAllCategories();
    }
}
