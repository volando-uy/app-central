package infra.repository.buypackage;

import domain.models.buypackage.BuyPackage;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.models.user.Customer;

public interface IBuyPackageRepository {
    void buyPackage(BuyPackage buyPackage, Customer customer, FlightRoutePackage flightRoutePackage);
    BuyPackage getFullBuyPackageById(Long id);
    BuyPackage getBuyPackageById(Long id);
}
