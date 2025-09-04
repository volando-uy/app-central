package infra.repository.flight;

import domain.models.flight.Flight;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;

public interface IFlightRepository {
    Flight getFlightByName(String flightName);
    Flight getFullFlightByName(String flightName);

    List<Flight> getAllByAirlineNickname(String airlineNickname);

    boolean existsByName(@NotBlank @Size(min = 2, max = 100) String name);
}
