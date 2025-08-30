package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoutePackage.FlightRoutePackage;

import java.util.List;

public interface IFlightRoutePackageService {
    FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO);
    FlightRoutePackageDTO getFlightRoutePackageByName(String flightRoutePackageName);
    boolean flightRoutePackageExists(String flightRoutePackageName);
    List<String> getAllNotBoughtFlightRoutePackagesNames();
    void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity);
    List<FlightRoutePackageDTO> getPackagesWithFlightRoutes();

    List<FlightRoutePackage> getAllFlightRoutePackages();
}