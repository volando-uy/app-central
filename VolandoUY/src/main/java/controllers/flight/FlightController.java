package controllers.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.services.flight.IFlightService;
import lombok.AllArgsConstructor;

import java.io.File;
import java.util.List;

@AllArgsConstructor
public class FlightController implements IFlightController {

    private final IFlightService flightService;

    @Override
    public BaseFlightDTO createFlight(
            BaseFlightDTO baseFlightDTO,
            String airlineNickname,
            String flightRouteName,
            File imageFile
    ) {
        return flightService.createFlight(baseFlightDTO, airlineNickname, flightRouteName, imageFile);
    }

    @Override
    public List<FlightDTO> getAllFlightsDetails() {
        return flightService.getAllFlights(true);
    }

    @Override
    public List<BaseFlightDTO> getAllFlightsSimpleDetails() {
        return flightService.getAllFlights(false)
                .stream()
                .map(flightDTO -> (BaseFlightDTO) flightDTO)
                .toList();
    }

    @Override
    public FlightDTO getFlightDetailsByName(String flightName) {
        return flightService.getFlightDetailsByName(flightName, true);
    }

    @Override
    public BaseFlightDTO getFlightSimpleDetailsByName(String flightName) {
        return flightService.getFlightDetailsByName(flightName, false);
    }

    @Override
    public List<FlightDTO> getAllFlightsDetailsByAirline(String airlineNickname) {
        return flightService.getAllFlightsByAirlineNickname(airlineNickname, true);
    }


    @Override
    public List<BaseFlightDTO> getAllFlightsSimpleDetailsByAirline(String airlineNickname) {
        return flightService.getAllFlightsByAirlineNickname(airlineNickname, false)
                .stream()
                .map(flightDTO -> (BaseFlightDTO) flightDTO)
                .toList();
    }

    @Override
    public List<FlightDTO> getAllFlightsDetailsByRouteName(String flightRouteName) {
        return flightService.getAllFlightsByRouteName(flightRouteName, true);
    }

    @Override
    public List<BaseFlightDTO> getAllFlightsSimpleDetailsByRouteName(String flightRouteName) {
        return flightService.getAllFlightsByRouteName(flightRouteName, false)
                .stream()
                .map(flightDTO -> (BaseFlightDTO) flightDTO)
                .toList();
    }

}
