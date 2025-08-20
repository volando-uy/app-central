package controllers.flight;

import domain.dtos.flight.FlightDTO;

import java.util.List;

public interface IFlightController {
    FlightDTO createFlight(FlightDTO flight);

    List<FlightDTO> getAllFlights();

    FlightDTO getFlightByName(String name);

    List<FlightDTO> getAllFlightsByAirline(String airlineNickname);
}
