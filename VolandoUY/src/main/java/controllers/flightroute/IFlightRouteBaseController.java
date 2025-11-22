package controllers.flightroute;

import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;

import java.io.File;
import java.util.List;

public interface IFlightRouteBaseController {
    boolean existFlightRoute(String name);
    void setFlightRouteStatusByName(String routeName, String status);
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
