
package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import lombok.AllArgsConstructor;

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
    public boolean flightRoutePackageExists(String packageName) {
        return packageService.flightRoutePackageExists(packageName);
    }


}