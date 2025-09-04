package controllers.flightRoute;



import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flight.FlightDTO;
import java.util.List;

public interface IFlightRouteController {
    boolean existFlightRoute(String name);
    BaseFlightRouteDTO createFlightRoute(BaseFlightRouteDTO baseFlightRouteDTO, String originCityName, String destinationCityName, String airlineNickname, List<String> categoriesNames);

    // Get flight route by name
    // Simple and full versions
    FlightRouteDTO getFlightRouteDetailsByName(String routeName);
    BaseFlightRouteDTO getFlightRouteSimpleDetailsByName(String routeName);

    // Get all flight routes by airline nickname
    // Simple and full versions
    List<FlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname);
    List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByAirlineNickname(String airlineNickname);
}
