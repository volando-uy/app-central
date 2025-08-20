package domain.services.flightRoute;



import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;

import java.util.List;

public interface IFlightRouteService {
    public boolean existFlightRoute(String name);
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO,String airlineNickname);
    public List<FlightRouteDTO> getAllFlightRoutes();
    List<FlightRouteDTO> getFlightRoutesByAirline(String airlineNickname);
    FlightRouteDTO getFlightRouteByName(String routeName);

}
