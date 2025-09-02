package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.services.flightRoute.IFlightRouteService;

import java.util.List;

public interface IFlightRoutePackageService {
    FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO);

    // Solo para usarse entre servicios
    void _updateFlightRoutePackage(FlightRoutePackage flightRoutePackage);

    FlightRoutePackageDTO getFlightRoutePackageDetailsByName(String flightRoutePackageName);
    FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName);

    boolean flightRoutePackageExists(String flightRoutePackageName);
    List<String> getAllNotBoughtFlightRoutePackagesNames();
    void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity);
    List<FlightRoutePackageDTO> getPackagesWithFlightRoutes();

    List<FlightRoutePackage> getAllFlightRoutePackages();

    void setFlightRouteService(IFlightRouteService flightRouteService);
}