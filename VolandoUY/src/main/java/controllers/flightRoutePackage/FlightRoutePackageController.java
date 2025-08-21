
package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor

public class FlightRoutePackageController implements IFlightRoutePackageController {
    private IFlightRoutePackageService packageService;


    @Override
    public void createFlightRoutePackage(FlightRoutePackageDTO packageDTO) {
        this.packageService.addFlightRoutePackage(packageDTO);
    }

    @Override
    public void updateFlightRoutePackage(FlightRoutePackageDTO packageDTO) {
        this.packageService.updateFlightRoutePackage(packageDTO);
    }

    @Override
    public void deleteFlightRoutePackage(String packageName) {
        this.packageService.deleteFlightRoutePackage(packageName);
    }

    @Override
    public FlightRoutePackageDTO getFlightRoutePackage(String packageName) {
        return this.packageService.getFlightRoutePackage(packageName);
    }

    @Override
    public boolean flightRoutePackageExists(String packageName) {
        return this.packageService.flightRoutePackageExists(packageName);
    }

    @Override
    public List<FlightRoutePackageDTO> getAllFlightRoutePackage( ) {
        return this.packageService.getAllFlightRoutePackage();
    }


}