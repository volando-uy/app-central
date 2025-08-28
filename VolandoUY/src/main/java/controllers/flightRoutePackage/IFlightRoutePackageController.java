package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoutePackage.FlightRoutePackage;

import java.util.List;

public interface IFlightRoutePackageController {
    FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO);
    FlightRoutePackageDTO getFlightRoutePackageByName(String packageName);
    List<String> getAllNotBoughtFlightRoutePackagesNames();
    void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity);
    boolean flightRoutePackageExists(String packageName);

    List<FlightRoutePackage> getAllFlightRoutePackages();
}