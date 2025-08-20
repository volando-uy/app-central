
package controllers.flightRoutePackage;

import domain.dtos.packages.PackageDTO;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class FlightRoutePackageController implements IFlightRoutePackageController {
    private IFlightRoutePackageService packageService;

    @Override
    public void addPackage(PackageDTO packageDTO) {
        this.packageService.addPackage(packageDTO);
    }

    @Override
    public void updatePackage(PackageDTO packageDTO) {
        this.packageService.updatePackage(packageDTO);
    }

    @Override
    public void deletePackage(String packageName) {
        this.packageService.deletePackage(packageName);
    }

    @Override
    public PackageDTO getPackage(String packageName) {
        return this.packageService.getPackage(packageName);
    }

    @Override
    public boolean packageExists(String packageName) {
        return this.packageService.packageExists(packageName);
    }


}