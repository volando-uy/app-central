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
        return this.flightRouteService.existFlightRoute(name);
    }
    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO){
        return this.flightRouteService.createFlightRoute(flightRouteDTO);
    }
    @Override
    public List<FlightRouteDTO> getAllFlightRoutes(){
        return this.flightRouteService.getAllFlightRoutes();
    }
}
