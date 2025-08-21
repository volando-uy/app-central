package domain.services.flightRoute;



import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flightRoute.FlightRoute;

import java.util.List;

public interface IFlightRouteService {
    FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO,String airlineNickname);
    FlightRouteDTO getFlightRouteDetailsByName(String routeName);
    FlightRoute getFlightRouteByName(String routeName);
    boolean existFlightRoute(String name);

    List<FlightRouteDTO> getAllFlightRoutesByAirline(String airlineNickname);
}
