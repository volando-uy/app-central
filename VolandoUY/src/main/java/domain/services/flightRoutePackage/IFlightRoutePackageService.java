package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;

import java.util.List;

public interface IFlightRoutePackageService {
    FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO);
    FlightRoutePackageDTO getFlightRoutePackageByName(String flightRoutePackageName);
    boolean flightRoutePackageExists(String flightRoutePackageName);
    List<String> getAllNotBoughtFlightRoutePackagesNames();
    void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity);
}