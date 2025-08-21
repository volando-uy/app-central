package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;


public interface IFlightRoutePackageController {
    FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO);
    FlightRoutePackageDTO getFlightRoutePackageByName(String packageName);
    boolean flightRoutePackageExists(String packageName);
}

