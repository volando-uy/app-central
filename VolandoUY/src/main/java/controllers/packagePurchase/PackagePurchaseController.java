package controllers.packagePurchase;

import domain.services.packagePurchaseService.IPackagePurchaseService;

public class PackagePurchaseController implements IPackagePurchaseController {

    private final IPackagePurchaseService packagePurchaseService;

    public PackagePurchaseController(IPackagePurchaseService packagePurchaseService) {
        this.packagePurchaseService = packagePurchaseService;
    }

    @Override
    public void purchasePackage(String customerNickname, String packageName) {
        packagePurchaseService.purchasePackage(customerNickname, packageName);
    }
}
