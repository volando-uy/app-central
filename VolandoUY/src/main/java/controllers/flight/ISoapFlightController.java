package controllers.flight;

import app.adapters.dto.flights.SoapBaseFlightDTO;
import app.adapters.dto.flights.SoapFlightDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;

import java.io.File;
import java.util.List;

public interface ISoapFlightController extends IBaseFlightController {
    List<SoapBaseFlightDTO> getAllFlightsSimpleDetails();

    List<SoapFlightDTO> getAllFlightsDetailsByRouteName(String flightRouteName);

    List<SoapBaseFlightDTO> getAllFlightsSimpleDetailsByRouteName(String flightRouteName);

    List<SoapBaseFlightDTO> getAllFlightsSimpleDetailsByAirline(String airlineNickname);

    List<SoapFlightDTO> getAllFlightsDetailsByAirline(String airlineNickname);

    SoapBaseFlightDTO getFlightSimpleDetailsByName(String name);

    SoapFlightDTO getFlightDetailsByName(String name);
    List<SoapFlightDTO> getAllFlightsDetails();
    SoapBaseFlightDTO createFlight(SoapBaseFlightDTO flight, String airlineNickname, String flightRouteName, String imageBase64);

}
