package infra.repository.flightroutepackage;

import domain.models.flightRoutePackage.FlightRoutePackage;

public interface IFlightRoutePackageRepository {
    FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName);
    FlightRoutePackage getFullFlightRoutePackageByName(String flightRoutePackageName);

    boolean existsByName(String packageName);
}
