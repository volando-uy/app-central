package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoutePackage.FlightRoutePackage;

import java.util.List;

public interface IFlightRoutePackageController {
    BaseFlightRoutePackageDTO createFlightRoutePackage(BaseFlightRoutePackageDTO flightRoutePackageDTO);

    // Getters for flight route package by name
    // Simple and full versions
    FlightRoutePackageDTO getFlightRoutePackageDetailsByName(String packageName);
    BaseFlightRoutePackageDTO getFlightRoutePackageSimpleDetailsByName(String packageName);

    // Getters for all flight route packages that include flight routes
    // Only simple, I dont think full is needed
    List<BaseFlightRoutePackageDTO> getAllFlightRoutesPackagesSimpleDetailsWithFlightRoutes();

    List<FlightRoutePackage> getAllFlightRoutesPackages();

    // Getters for all flight route packages
    // Simple and full versions
    List<FlightRoutePackageDTO> getAllFlightRoutesPackagesDetails();
    List<BaseFlightRoutePackageDTO> getAllFlightRoutesPackagesSimpleDetails();

    List<String> getAllNotBoughtFlightRoutesPackagesNames();
    void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity);
    boolean flightRoutePackageExists(String packageName);
}