package domain.services.flightroutepackage;

import domain.dtos.flightroutepackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.services.flightroute.IFlightRouteService;

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

    FlightRoutePackage getFlightRoutePackageDetailsById(Long id);
}