package infra.repository.flightroute;

import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;

import java.util.Arrays;
import java.util.List;

public interface IFlightRouteRepository {
    boolean existsByName(String name);

    Iterable<FlightRoute> getAllByAirlineNickname(String airlineNickname);
    Iterable<FlightRoute> getFullAllByAirlineNickname(String airlineNickname);
    void saveFlightRouteAndAddToAirline(FlightRoute flightRoute, Airline airline);


    FlightRoute getByName(String routeName);
}
