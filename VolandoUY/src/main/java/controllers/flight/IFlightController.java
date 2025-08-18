package controllers.flight;

import domain.dtos.flight.FlightDTO;

import java.util.List;

public interface IFlightController {
    FlightDTO createFlight(FlightDTO flight);
    List<FlightDTO> getAllFlights();
}
