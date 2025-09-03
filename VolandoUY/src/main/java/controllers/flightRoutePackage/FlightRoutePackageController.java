
package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
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
    public BaseFlightRoutePackageDTO createFlightRoutePackage(BaseFlightRoutePackageDTO baseFlightRoutePackageDTO) {
        return packageService.createFlightRoutePackage(baseFlightRoutePackageDTO);
    }
    @Override
    public FlightRoutePackageDTO getFlightRoutePackageDetailsByName(String packageName) {
        return packageService.getFlightRoutePackageDetailsByName(packageName, true);
    }

    @Override
    public BaseFlightRoutePackageDTO getFlightRoutePackageSimpleDetailsByName(String packageName) {
        return packageService.getFlightRoutePackageDetailsByName(packageName, false);
    }

    @Override
    public List<String> getAllNotBoughtFlightRoutesPackagesNames() {
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
    public List<BaseFlightRoutePackageDTO> getAllFlightRoutesPackagesSimpleDetailsWithFlightRoutes() {
        return packageService.getAllFlightRoutePackagesWithFlightRoutes(false)
                .stream()
                .map(pack -> (BaseFlightRoutePackageDTO) pack)
                .toList();
    }

    @Override
    public List<FlightRoutePackage> getAllFlightRoutesPackages() {
        return packageService.getAllFlightRoutePackages();
    }

    @Override
    public List<FlightRoutePackageDTO> getAllFlightRoutesPackagesDetails() {
        return packageService.getAllFlightRoutePackagesDetails(true);
    }

    @Override
    public List<BaseFlightRoutePackageDTO> getAllFlightRoutesPackagesSimpleDetails() {
        return packageService.getAllFlightRoutePackagesDetails(false).stream()
                .map(pack -> (BaseFlightRoutePackageDTO) pack)
                .toList();
    }
}