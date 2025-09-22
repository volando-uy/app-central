package domain.services.buyPackage;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.flight.Flight;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.luggage.Luggage;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import domain.services.flight.IFlightService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.seat.ISeatService;
import domain.services.ticket.ITicketService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.booking.BookingRepository;
import infra.repository.buyPackage.BuyPackageRepository;
import infra.repository.buyPackage.IBuyPackageRepository;
import lombok.Setter;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyPackageService implements IBuyPackageService {

    @Setter
    private IUserService userService;

    @Setter
    private IFlightRoutePackageService flightRoutePackageService;

    private final IBuyPackageRepository buyPackageRepository;

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public BuyPackageService() {
        this.buyPackageRepository = RepositoryFactory.getBuyPackageRepository();
    }


    @Override
    public BaseBuyPackageDTO createBuyPackage(String customerNickname, String flightRoutePackageName) {
        // Buscar el cliente por su nickname
        Customer customer = userService.getCustomerByNickname(customerNickname, true);

        // Buscar el paquete por su nombre
        FlightRoutePackage flightRoutePackage = flightRoutePackageService.getFlightRoutePackageByName(flightRoutePackageName, true);


        // Verificar si ya compró este paquete
        customer.getBoughtPackages().stream()
                .filter(pkg -> pkg.getFlightRoutePackage().getName().equalsIgnoreCase(flightRoutePackageName))
                .findFirst()
                .ifPresent(pkg -> {
                    throw new IllegalArgumentException(
                            String.format("El cliente %s ya compró el paquete %s", customerNickname, flightRoutePackageName)
                    );
                });

        // Validar que el paquete no esté vencido
        if (flightRoutePackage.isExpired()) {
            throw new IllegalArgumentException(
                    String.format("El paquete %s ya expiró y no puede ser comprado", flightRoutePackageName)
            );
        }

        // Creamos el buy package
        BuyPackage buyPackage = new BuyPackage();
        buyPackage.setCustomer(customer);
        buyPackage.setFlightRoutePackage(flightRoutePackage);
        buyPackage.setCreatedAt(LocalDateTime.now());
        buyPackage.setTotalPrice(flightRoutePackage.getTotalPrice());
        buyPackage.setBookFlights(new ArrayList<>());

        buyPackageRepository.buyPackage(buyPackage, customer, flightRoutePackage);

        return customModelMapper.map(buyPackage, BaseBuyPackageDTO.class);
    }

    @Override
    public BuyPackageDTO getBuyPackageDetailsById(Long id, boolean full) {
        BuyPackage buyPackage = full ? buyPackageRepository.getFullBuyPackageById(id) : buyPackageRepository.getBuyPackageById(id);
        if (buyPackage == null) {
            throw new IllegalArgumentException(String.format("No se encontró la compra de paquete con ID %d", id));
        }

        return full ? customModelMapper.mapFullBuyPackage(buyPackage)
                : customModelMapper.map(buyPackage, BuyPackageDTO.class);
    }

}
