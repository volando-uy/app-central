package controllers.flightroute;

import domain.dtos.flightroute.BaseFlightRouteDTO;

import java.util.List;

public interface IFlightRouteSoapController extends IFlightRouteBaseController {
    BaseFlightRouteDTO createFlightRoute(BaseFlightRouteDTO dto,
                                         String origin,
                                         String destination,
                                         String airlineNickname,
                                         List<String> categories,
                                         String imageBase64);
}
