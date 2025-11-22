package controllers.flightroute;



import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;

import java.io.File;
import java.util.List;

public interface IFlightRouteController extends IFlightRouteBaseController {
    BaseFlightRouteDTO createFlightRoute(BaseFlightRouteDTO baseFlightRouteDTO, String originAeroCode, String destinationAeroCode, String airlineNickname, List<String> categoriesNames, File imageFile);
}
