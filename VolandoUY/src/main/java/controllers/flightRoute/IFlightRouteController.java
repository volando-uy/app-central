package controllers.flightRoute;



import domain.dtos.flightRoute.FlightRouteDTO;

import java.util.List;

public interface IFlightRouteController {
    public boolean existFlightRoute(String name);
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO);
    public List<FlightRouteDTO> getAllFlightRoutes();
}
