package infra.repository.flightroutepackage;

import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import infra.repository.IBaseRepository;

import java.util.List;

public interface IFlightRoutePackageRepository extends IBaseRepository<FlightRoutePackage> {
    FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName);

    FlightRoutePackage getFullFlightRoutePackageByName(String flightRoutePackageName);

    boolean existsByName(String packageName);

    //    List<BuyPackage> findByCustomerNickname(String nickname);
    FlightRoutePackage getFlightRoutePackageFullByName(String flightRoutePackageName);

    void addFlightRouteToPackage(FlightRoute flightRoute, FlightRoutePackage flightRoutePackage);

    List<FlightRoutePackage> findAllWithFlightRoutes();


    List<FlightRoutePackage> findAllFullWithFlightRoutes();
}
