package domain.services.buypackage;

import domain.dtos.buypackage.BaseBuyPackageDTO;
import domain.dtos.buypackage.BuyPackageDTO;
import domain.services.flightroutepackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import infra.repository.buypackage.IBuyPackageRepository;

public interface IBuyPackageService {
    BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName);

    void setUserService(IUserService userService);
    void setFlightRoutePackageService(IFlightRoutePackageService flightRoutePackageService);

    void setBuyPackageRepository(IBuyPackageRepository buyPackageRepository);

    BuyPackageDTO getBuyPackageDetailsById(Long id, boolean full);

    String getFlightNameById(Long id);
}
