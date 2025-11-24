package controllers.buypackage;


import domain.dtos.buypackage.BaseBuyPackageDTO;
import domain.dtos.buypackage.BuyPackageDTO;

public interface IBuyPackageController {
    BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName);
    BuyPackageDTO getBuyPackageDetailsById(Long id);
    BaseBuyPackageDTO getBuyPackageSimpleDetailsById(Long id);

    String getFlightNameById(Long id);
}
