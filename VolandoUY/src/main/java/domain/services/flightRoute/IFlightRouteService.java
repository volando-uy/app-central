package domain.services.flightRoute;



import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flightRoute.FlightRoute;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;

import java.util.List;

public interface IFlightRouteService {
    FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO);
    FlightRouteDTO getFlightRouteDetailsByName(String routeName, boolean full);
    FlightRoute getFlightRouteByName(String routeName);
    boolean existFlightRoute(String name);
    List<FlightRouteDTO> getFlightRoutesDetailsByAirlineNickname(String airlineNickname, boolean full);

    void setCategoryService(ICategoryService categoryService);
    void setCityService(ICityService cityService);
    void setUserService(IUserService userService);
}
