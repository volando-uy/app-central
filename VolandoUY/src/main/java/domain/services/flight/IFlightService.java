package domain.services.flight;

import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;

import java.util.List;

public interface IFlightService {
    FlightDTO createFlight(FlightDTO flightDTO);
    List<FlightDTO> getAllFlights();
    FlightDTO getFlightDetailsByName(String flightName);
    Flight getFlightByName(String flightName);
    List<FlightDTO> getAllFlightsByAirline(String airlineNickname);
}
