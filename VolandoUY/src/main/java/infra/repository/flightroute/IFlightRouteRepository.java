package infra.repository.flightroute;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Airline;
import infra.repository.IBaseRepository;

import java.util.Arrays;
import java.util.List;

public interface IFlightRouteRepository extends IBaseRepository<FlightRoute> {
    boolean existsByName(String name);

    Iterable<FlightRoute> getAllByAirlineNickname(String airlineNickname);
    Iterable<FlightRoute> getFullAllByAirlineNickname(String airlineNickname);

    FlightRoute getByName(String routeName);
    FlightRoute getFullByName(String routeName);

    void createFlightRoute(FlightRoute flightRoute, Airline airline);

    List<FlightRoute> getFullAllByPackageName(String packageName);

    List<FlightRoute> getAllByPackageName(String packageName);
}
