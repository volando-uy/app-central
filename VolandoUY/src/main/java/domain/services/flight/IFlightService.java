package domain.services.flight;

import domain.dtos.flight.FlightDTO;

import java.util.List;

public interface IFlightService {
    FlightDTO createFlight(FlightDTO flight);
    List<FlightDTO> getAllFlights();
}
