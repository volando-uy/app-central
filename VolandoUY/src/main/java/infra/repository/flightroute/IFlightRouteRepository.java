package infra.repository.flightroute;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Airline;

import java.util.Arrays;
import java.util.List;

public interface IFlightRouteRepository {
    boolean existsByName(String name);

    Iterable<FlightRoute> getAllByAirlineNickname(String airlineNickname);
    Iterable<FlightRoute> getFullAllByAirlineNickname(String airlineNickname);

    FlightRoute getByName(String routeName);
    FlightRoute getFullByName(String routeName);

    void createFlightRoute(FlightRoute flightRoute, Airline airline);
}
