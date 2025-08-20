package domain.services.flightRoute;



import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;

import java.util.List;

public interface IFlightRouteService {
    public boolean existFlightRoute(String name);
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO);
    public List<FlightRouteDTO> getAllFlightRoutes();
}
