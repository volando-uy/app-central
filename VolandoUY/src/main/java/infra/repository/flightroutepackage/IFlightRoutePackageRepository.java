package infra.repository.flightroutepackage;

import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.flightRoutePackage.FlightRoutePackage;

import java.util.List;

public interface IFlightRoutePackageRepository {
    FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName);
    FlightRoutePackage getFullFlightRoutePackageByName(String flightRoutePackageName);
    boolean existsByName(String packageName);
    List<BuyPackage> findByCustomerNickname(String nickname);
}
