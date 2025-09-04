package controllers.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;

import java.util.List;

public interface IFlightController {
    BaseFlightDTO createFlight(BaseFlightDTO flight, String airlineNickname, String flightRouteName);

    List<FlightDTO> getAllFlights();

    FlightDTO getFlightByName(String name);

    List<FlightDTO> getAllFlightsByAirline(String airlineNickname);

    List<FlightDTO> getAllFlightsByRouteName(String flightRouteName);
}
