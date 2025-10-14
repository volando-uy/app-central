package controllers.flightRoute;

import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import lombok.AllArgsConstructor;

import java.io.File;
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
            String originAeroCode,
            String destinationAeroCode,
            String airlineNickname,
            List<String> categoriesNames,
            File imageFile
    ){
        return flightRouteService.createFlightRoute(
                baseFlightRouteDTO,
                originAeroCode,
                destinationAeroCode,
                airlineNickname,
                categoriesNames,
                imageFile
        );
    }

    @Override
    public void setStatusFlightRouteByName(String routeName, boolean confirmed) {
        flightRouteService.setStatusFlightRouteByName(routeName, confirmed);
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

    @Override
    public List<FlightRouteDTO> getAllFlightRoutesDetailsByPackageName(String packageName) {
        return flightRouteService.getFlightRoutesDetailsByPackageName(packageName, true);
    }

    @Override
    public List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByPackageName(String packageName) {
        return flightRouteService.getFlightRoutesDetailsByPackageName(packageName, false)
                .stream()
                .map(route -> (BaseFlightRouteDTO) route)
                .toList();
    }
}
