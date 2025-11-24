package controllers.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;

import java.io.File;
import java.util.List;

public interface IFlightController extends IBaseFlightController {
    List<BaseFlightDTO> getAllFlightsSimpleDetails();
    List<FlightDTO> getAllFlightsDetailsByRouteName(String flightRouteName);
    List<BaseFlightDTO> getAllFlightsSimpleDetailsByRouteName(String flightRouteName);
    List<BaseFlightDTO> getAllFlightsSimpleDetailsByAirline(String airlineNickname);
    List<FlightDTO> getAllFlightsDetailsByAirline(String airlineNickname);
    BaseFlightDTO getFlightSimpleDetailsByName(String name);
    FlightDTO getFlightDetailsByName(String name);
    List<FlightDTO> getAllFlightsDetails();
    BaseFlightDTO createFlight(BaseFlightDTO flight, String airlineNickname, String flightRouteName, File imageFile);

}
