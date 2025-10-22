package domain.services.buyPackage;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.services.flight.IFlightService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.seat.ISeatService;
import domain.services.ticket.ITicketService;
import domain.services.user.IUserService;
import infra.repository.buyPackage.IBuyPackageRepository;

import java.util.List;
import java.util.Map;

public interface IBuyPackageService {
    BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName);

    void setUserService(IUserService userService);
    void setFlightRoutePackageService(IFlightRoutePackageService flightRoutePackageService);

    void setBuyPackageRepository(IBuyPackageRepository buyPackageRepository);

    BuyPackageDTO getBuyPackageDetailsById(Long id, boolean full);
}
