package controllers.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;

import java.io.File;
import java.util.List;

public interface IBaseFlightController {
    BaseFlightDTO createFlight(BaseFlightDTO flight, String airlineNickname, String flightRouteName, File imageFile);

    List<FlightDTO> getAllFlightsDetails();

    FlightDTO getFlightDetailsByName(String name);
    BaseFlightDTO getFlightSimpleDetailsByName(String name);

    List<FlightDTO> getAllFlightsDetailsByAirline(String airlineNickname);
    List<BaseFlightDTO> getAllFlightsSimpleDetailsByAirline(String airlineNickname);

    List<FlightDTO> getAllFlightsDetailsByRouteName(String flightRouteName);
    List<BaseFlightDTO> getAllFlightsSimpleDetailsByRouteName(String flightRouteName);
}
