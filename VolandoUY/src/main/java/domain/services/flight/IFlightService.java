package domain.services.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.seat.ISeatService;
import domain.services.user.IUserService;

import java.io.File;
import java.util.List;

public interface IFlightService {
    BaseFlightDTO createFlight(BaseFlightDTO baseFlightDTO, String airlineNickname, String flightRouteName, File imageFile);

    List<FlightDTO> getAllFlights(boolean full);

    FlightDTO getFlightDetailsByName(String flightName, boolean full);

    Flight getFlightByName(String flightName, boolean full);

    List<FlightDTO> getAllFlightsByAirlineNickname(String airlineNickname, boolean full);

    List<FlightDTO> getAllFlightsByRouteName(String flightRouteName, boolean full);

    void setUserService(IUserService userService);
    void setFlightRouteService(IFlightRouteService flightRouteService);
    void setSeatService(ISeatService seatService);
}