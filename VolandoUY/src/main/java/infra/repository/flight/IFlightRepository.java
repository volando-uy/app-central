package infra.repository.flight;

import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.seat.Seat;
import domain.models.user.Airline;
import infra.repository.IBaseRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;

public interface IFlightRepository extends IBaseRepository<Flight> {
    Flight getFlightByName(String flightName);
    Flight getFullFlightByName(String flightName);

    List<Flight> getAllByAirlineNickname(String airlineNickname);

    boolean existsByName(@NotBlank @Size(min = 2, max = 100) String name);

    List<Flight> getFlightsByRouteName(String routeName);

    void saveFlightWithSeatsAndAddToAirlineAndAddToFlightRoute(Flight flight, Airline airline, FlightRoute flightRoute, List<Seat> seats);
}
