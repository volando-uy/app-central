
package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor

public class FlightRoutePackageController implements IFlightRoutePackageController {
    private IFlightRoutePackageService packageService;

    @Override
    public FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO) {
        return packageService.createFlightRoutePackage(flightRoutePackageDTO);
    }
    @Override
    public FlightRoutePackageDTO getFlightRoutePackageByName(String packageName) {
        return packageService.getFlightRoutePackageByName(packageName);
    }

    @Override
    public List<String> getAllNotBoughtFlightRoutePackagesNames() {
        return packageService.getAllNotBoughtFlightRoutePackagesNames();
    }

    @Override
    public void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity) {
        packageService.addFlightRouteToPackage(packageName, flightRouteName, quantity);
    }

    @Override
    public boolean flightRoutePackageExists(String packageName) {
        return packageService.flightRoutePackageExists(packageName);
    }

    @Override
    public List<FlightRoutePackage> getAllFlightRoutePackages() {
        return packageService.getAllFlightRoutePackages();
    }
}