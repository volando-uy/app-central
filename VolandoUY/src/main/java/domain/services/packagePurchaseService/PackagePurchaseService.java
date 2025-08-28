package domain.services.packagePurchaseService;

import domain.models.packagePurchase.PackagePurchase;
import domain.models.user.Customer;
import domain.models.flightRoutePackage.FlightRoutePackage;
import infra.repository.compraPaquete.PackagePurchaseRepository;
import domain.services.user.IUserService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import org.modelmapper.ModelMapper;

public class PackagePurchaseService implements IPackagePurchaseService {

    private final PackagePurchaseRepository packagePurchaseRepository;
    private final IUserService userService;
    private final IFlightRoutePackageService flightRoutePackageService;
    private final ModelMapper modelMapper;

    public PackagePurchaseService(PackagePurchaseRepository packagePurchaseRepository,
                                  IUserService userService,
                                  IFlightRoutePackageService flightRoutePackageService,
                                  ModelMapper modelMapper) {
        this.packagePurchaseRepository = packagePurchaseRepository;
        this.userService = userService;
        this.flightRoutePackageService = flightRoutePackageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void purchasePackage(String customerNickname, String packageName) {
        // Mapear DTOs a entidades
        Customer customer = modelMapper.map(
                userService.getCustomerDetailsByNickname(customerNickname),
                Customer.class
        );

        FlightRoutePackage flightRoutePackage = modelMapper.map(
                flightRoutePackageService.getFlightRoutePackageByName(packageName),
                FlightRoutePackage.class
        );

        // Verificar si ya compró este paquete
        if (packagePurchaseRepository.existsByCustomerAndPackage(customerNickname, packageName)) {
            throw new IllegalArgumentException(
                    String.format("El cliente %s ya compró el paquete %s",
                            customerNickname, packageName)
            );
        }

        // Validar que el paquete no esté vencido
        if (flightRoutePackage.isExpired()) {
            throw new IllegalArgumentException(
                    String.format("El paquete %s ya expiró y no puede ser comprado", packageName)
            );
        }

        // Crear compra (el constructor valida y setea fechas)
        PackagePurchase purchase = new PackagePurchase(customer, flightRoutePackage);

        // Guardar
        packagePurchaseRepository.save(purchase);
    }
}
