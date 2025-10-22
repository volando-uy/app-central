package controllers.flightroute;



import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;

import java.io.File;
import java.util.List;

public interface IFlightRouteController {
    boolean existFlightRoute(String name);
    BaseFlightRouteDTO createFlightRoute(BaseFlightRouteDTO baseFlightRouteDTO, String originAeroCode, String destinationAeroCode, String airlineNickname, List<String> categoriesNames, File imageFile);
    void setStatusFlightRouteByName(String routeName, boolean confirmed);
    // Get flight route by name
    // Simple and full versions
    FlightRouteDTO getFlightRouteDetailsByName(String routeName);
    BaseFlightRouteDTO getFlightRouteSimpleDetailsByName(String routeName);

    // Get all flight routes by airline nickname
    // Simple and full versions
    List<FlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname);
    List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByAirlineNickname(String airlineNickname);

    List<FlightRouteDTO> getAllFlightRoutesDetailsByPackageName(String packageName);
    List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByPackageName(String packageName);
}
