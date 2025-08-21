package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;

import java.util.List;

public interface IFlightRoutePackageService {
    void addFlightRoutePackage(FlightRoutePackageDTO packageDTO);
    void updateFlightRoutePackage(FlightRoutePackageDTO packageDTO);
    void deleteFlightRoutePackage(String packageName);
    FlightRoutePackageDTO getFlightRoutePackage(String packageName);
    boolean flightRoutePackageExists(String packageName);
    List<FlightRoutePackageDTO> getAllFlightRoutePackage();
}