package controllers.flightRoute;



import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flight.FlightDTO;
import java.util.List;

public interface IFlightRouteController {
    boolean existFlightRoute(String name);
    FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO);
    FlightRouteDTO getFlightRouteByName(String routeName);
    List<FlightRouteDTO> getAllFlightRoutesByAirlineNickname(String airlineNickname);
    List<FlightDTO> getFlightsFromRoute(String routeName);
}
