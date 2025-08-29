package domain.services.packagePurchaseService;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;

import java.util.List;

public interface IPackagePurchaseService {
    void purchasePackage(String customerNickname, String packageName);
}