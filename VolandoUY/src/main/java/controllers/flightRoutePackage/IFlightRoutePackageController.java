package controllers.flightRoutePackage;

import domain.dtos.packages.PackageDTO;

public interface IFlightRoutePackageController {
    void addPackage(PackageDTO packageDTO);
    void updatePackage(PackageDTO packageDTO);
    void deletePackage(String packageName);
    PackageDTO getPackage(String packageName);
    boolean packageExists(String packageName);
}