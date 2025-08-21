package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;

public interface IFlightRoutePackageService {
    FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO);
    FlightRoutePackageDTO getFlightRoutePackageByName(String flightRoutePackageName);
    boolean flightRoutePackageExists(String flightRoutePackageName);
}