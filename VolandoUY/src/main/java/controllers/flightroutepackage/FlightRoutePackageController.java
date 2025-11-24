
package controllers.flightroutepackage;

import domain.dtos.flightroutepackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.services.flightroutepackage.IFlightRoutePackageService;
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
        return packageService.getAllFlightRoutePackagesDetailsWithFlightRoutes(false)
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

    @Override
    public FlightRoutePackageDTO getFlightRoutePackageDetailsById(Long id) {
        FlightRoutePackage flightRoutePackage = packageService.getFlightRoutePackageDetailsById(id);
        return packageService.getFlightRoutePackageDetailsByName(flightRoutePackage.getName(), true);
    }

}