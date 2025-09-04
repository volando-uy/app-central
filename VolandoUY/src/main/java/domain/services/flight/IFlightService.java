package domain.services.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;

import java.util.List;

public interface IFlightService {
    BaseFlightDTO createFlight(BaseFlightDTO baseFlightDTO, String airlineNickname, String flightRouteName);

    List<FlightDTO> getAllFlights();

    FlightDTO getFlightDetailsByName(String flightName);

    Flight getFlightByName(String flightName);

    List<FlightDTO> getAllFlightsByAirline(String airlineNickname);

    List<FlightDTO> getFlightsByRouteName(String routeName);

    List<FlightDTO> getAllFlightsByRouteName(String flightRouteName);
}