package infra.repository.flightroute;

import domain.models.flightroute.FlightRoute;
import domain.models.user.Airline;
import infra.repository.IBaseRepository;

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

    void incrementVisitCount(String routeName);

    List<FlightRoute> getTopByVisitCount();
}
