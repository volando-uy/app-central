package controllers.flightRoute;



import domain.dtos.flightRoute.FlightRouteDTO;

import java.util.List;

public interface IFlightRouteController {
    boolean existFlightRoute(String name);
    FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO,String airlineNickname);
    List<FlightRouteDTO> getAllFlightRoutes();
    List<FlightRouteDTO> getFlightRoutesByAirline(String airlineNickname);
    FlightRouteDTO getFlightRouteByName(String routeName);

}
