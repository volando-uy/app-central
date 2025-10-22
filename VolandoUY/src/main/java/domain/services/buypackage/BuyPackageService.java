package domain.services.buypackage;

import domain.dtos.buypackage.BaseBuyPackageDTO;
import domain.dtos.buypackage.BuyPackageDTO;
import domain.models.buypackage.BuyPackage;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.models.user.Customer;
import domain.services.flightroutepackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.buypackage.IBuyPackageRepository;
import lombok.Setter;
import shared.utils.CustomModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BuyPackageService implements IBuyPackageService {

    @Setter
    private IUserService userService;

    @Setter
    private IFlightRoutePackageService flightRoutePackageService;

    @Setter
    private IBuyPackageRepository buyPackageRepository;

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
