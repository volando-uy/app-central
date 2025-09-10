package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.services.flightRoute.IFlightRouteService;

import java.util.Arrays;
import java.util.List;

public interface IFlightRoutePackageService {
    BaseFlightRoutePackageDTO createFlightRoutePackage(BaseFlightRoutePackageDTO baseFlightRoutePackageDTO);

    // Solo para usarse entre servicios
    void _updateFlightRoutePackage(FlightRoutePackage flightRoutePackage);

    FlightRoutePackageDTO getFlightRoutePackageDetailsByName(String flightRoutePackageName, boolean full);
    FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName, boolean full);

    boolean flightRoutePackageExists(String flightRoutePackageName);
    List<String> getAllNotBoughtFlightRoutePackagesNames();
    void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity);

    List<FlightRoutePackage> getAllFlightRoutePackages();
    List<FlightRoutePackageDTO> getAllFlightRoutePackagesDetails(boolean full);
    List<FlightRoutePackageDTO> getAllFlightRoutePackagesDetailsWithFlightRoutes(boolean full);

    void setFlightRouteService(IFlightRouteService flightRouteService);
}