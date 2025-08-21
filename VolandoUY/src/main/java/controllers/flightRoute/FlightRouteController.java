package controllers.flightRoute;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FlightRouteController implements IFlightRouteController{
    private IFlightRouteService flightRouteService;


    @Override
    public boolean existFlightRoute(String name){
        return flightRouteService.existFlightRoute(name);
    }

    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO){
        return flightRouteService.createFlightRoute(flightRouteDTO);
    }

    @Override
    public FlightRouteDTO getFlightRouteByName(String routeName) {
        return flightRouteService.getFlightRouteDetailsByName(routeName);
    }

    @Override
    public List<FlightRouteDTO> getAllFlightRoutesByAirlineNickname(String airlineNickname) {
        return flightRouteService.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);
    }
}
