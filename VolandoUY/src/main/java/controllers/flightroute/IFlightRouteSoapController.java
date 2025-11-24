package controllers.flightroute;

import app.adapters.dto.flightroute.SoapBaseFlightRouteDTO;
import app.adapters.dto.flightroute.SoapFlightRouteDTO;
import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;

import java.util.List;

public interface IFlightRouteSoapController extends IFlightRouteBaseController {
    SoapBaseFlightRouteDTO createFlightRoute(SoapBaseFlightRouteDTO dto,
                                             String origin,
                                             String destination,
                                             String airlineNickname,
                                             List<String> categories,
                                             String imageBase64);

    // Simple and full versions
    SoapFlightRouteDTO getFlightRouteDetailsByName(String routeName);
    SoapBaseFlightRouteDTO getFlightRouteSimpleDetailsByName(String routeName);

    // Get all flight routes by airline nickname
    // Simple and full versions
    List<SoapFlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname);
    List<SoapBaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByAirlineNickname(String airlineNickname);

    List<SoapFlightRouteDTO> getAllFlightRoutesDetailsByPackageName(String packageName);
    List<SoapBaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByPackageName(String packageName);
}
