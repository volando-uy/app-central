package controllers.flightRoute;

import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
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
    public BaseFlightRouteDTO createFlightRoute(
            BaseFlightRouteDTO baseFlightRouteDTO,
            String originCityName,
            String destinationCityName,
            String airlineNickname,
            List<String> categoriesNames
    ){
        return flightRouteService.createFlightRoute(
                baseFlightRouteDTO,
                originCityName,
                destinationCityName,
                airlineNickname,
                categoriesNames
        );
    }

    @Override
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName) {
        return flightRouteService.getFlightRouteDetailsByName(routeName, true);
    }

    @Override
    public BaseFlightRouteDTO getFlightRouteSimpleDetailsByName(String routeName) {
        return flightRouteService.getFlightRouteDetailsByName(routeName, false);
    }

    @Override
    public List<FlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname) {
        return flightRouteService.getFlightRoutesDetailsByAirlineNickname(airlineNickname, true);

    }

    @Override
    public List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByAirlineNickname(String airlineNickname) {
        return flightRouteService.getFlightRoutesDetailsByAirlineNickname(airlineNickname, false)
                .stream()
                .map(route -> (BaseFlightRouteDTO) route)
                .toList();
    }
}
