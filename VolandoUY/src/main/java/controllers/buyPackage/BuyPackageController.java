package controllers.buyPackage;


import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.category.CategoryDTO;
import domain.services.buyPackage.IBuyPackageService;
import domain.services.category.ICategoryService;
import lombok.AllArgsConstructor;

import java.util.List;

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
