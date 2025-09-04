package controllers.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.services.flight.IFlightService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FlightController implements IFlightController {

    private final IFlightService flightService;

    @Override
    public BaseFlightDTO createFlight(BaseFlightDTO baseFlightDTO, String airlineNickname, String flightRouteName) {
        return flightService.createFlight(baseFlightDTO, airlineNickname, flightRouteName);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @Override
    public FlightDTO getFlightByName(String flightName) {
        return flightService.getFlightDetailsByName(flightName);
    }

    @Override
    public List<FlightDTO> getAllFlightsByAirline(String airlineNickname) {
        return flightService.getAllFlightsByAirline(airlineNickname);
    }

    @Override
    public List<FlightDTO> getAllFlightsByRouteName(String flightRouteName) {
        return flightService.getAllFlightsByRouteName(flightRouteName);
    }
}
