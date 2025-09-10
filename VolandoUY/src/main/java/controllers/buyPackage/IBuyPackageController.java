package controllers.buyPackage;


import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.category.CategoryDTO;

import java.util.List;

public interface IBuyPackageController {
    BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName);
    BuyPackageDTO getBuyPackageDetailsById(Long id);
    BaseBuyPackageDTO getBuyPackageSimpleDetailsById(Long id);
}
