package infra.repository.buyPackage;

import domain.models.buypackage.BuyPackage;
import domain.models.category.Category;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Customer;

public interface IBuyPackageRepository {
    void buyPackage(BuyPackage buyPackage, Customer customer, FlightRoutePackage flightRoutePackage);
    BuyPackage getFullBuyPackageById(Long id);
    BuyPackage getBuyPackageById(Long id);
}
