package controllers.buypackage;


import domain.dtos.buypackage.BaseBuyPackageDTO;
import domain.dtos.buypackage.BuyPackageDTO;
import domain.services.buypackage.IBuyPackageService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BuyPackageController implements IBuyPackageController {
    private IBuyPackageService buyPackageService;

    @Override
    public BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName) {
        return buyPackageService.createBuyPackage(customerNickname, flightRoutePackageName);
    }

    @Override
    public BuyPackageDTO getBuyPackageDetailsById(Long id) {
        return buyPackageService.getBuyPackageDetailsById(id, true);
    }

    @Override
    public BaseBuyPackageDTO getBuyPackageSimpleDetailsById(Long id) {
        return buyPackageService.getBuyPackageDetailsById(id, false);
    }


}
