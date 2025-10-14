package domain.services.flightRoute;



import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flightRoute.FlightRoute;
import domain.services.airport.IAirportService;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;

import java.io.File;
import java.util.List;

public interface IFlightRouteService {
    BaseFlightRouteDTO createFlightRoute(BaseFlightRouteDTO baseFlightRouteDTO, String originAeroCode, String destinationAeroCode, String airlineNickname, List<String> categoriesNames, File imageFile);
    FlightRouteDTO getFlightRouteDetailsByName(String routeName, boolean full);

    FlightRoute getFlightRouteByName(String routeName, boolean full);

    boolean existFlightRoute(String name);
    List<FlightRouteDTO> getFlightRoutesDetailsByAirlineNickname(String airlineNickname, boolean full);

    void setCategoryService(ICategoryService categoryService);
    void setAirportService(IAirportService airportService);
    void setUserService(IUserService userService);

    List<FlightRouteDTO> getFlightRoutesDetailsByPackageName(String packageName, boolean full);

    void setStatusFlightRouteByName(String routeName, boolean confirmed);
}
