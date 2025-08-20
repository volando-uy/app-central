package controllers.packages;

import domain.dtos.packages.PackageDTO;

public interface IPackageController {
    void addPackage(PackageDTO packageDTO);
    void updatePackage(PackageDTO packageDTO);
    void deletePackage(String packageName);
    PackageDTO getPackage(String packageName);
    boolean packageExists(String packageName);
}