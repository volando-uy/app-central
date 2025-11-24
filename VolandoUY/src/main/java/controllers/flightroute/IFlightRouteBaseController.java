package controllers.flightroute;

import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;

import java.io.File;
import java.util.List;

public interface IFlightRouteBaseController {
    boolean existFlightRoute(String name);
    void setFlightRouteStatusByName(String routeName, String status);
    // Get flight route by name

}
