//package casosdeuso;
//
//import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
//import domain.dtos.user.CustomerDTO;
//import domain.models.enums.EnumTipoAsiento;
//import domain.models.packagePurchase.PackagePurchase;
//import domain.services.packagePurchaseService.IPackagePurchaseService;
//import domain.services.packagePurchaseService.PackagePurchaseService;
//import infra.repository.compraPaquete.PackagePurchaseRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class PackagePurchaseTest {
//
//    private PackagePurchaseRepository repository;
//    private IPackagePurchaseService packagePurchaseService;
//
//    // Dependencias mockeadas
//    private domain.services.user.IUserService userService;
//    private domain.services.flightRoutePackage.IFlightRoutePackageService flightRoutePackageService;
//
//    @BeforeEach
//    void setUp() {
//        repository = mock(PackagePurchaseRepository.class);
//        userService = mock(domain.services.user.IUserService.class);
//        flightRoutePackageService = mock(domain.services.flightRoutePackage.IFlightRoutePackageService.class);
//
//        packagePurchaseService = new PackagePurchaseService(
//                repository,
//                userService,
//                flightRoutePackageService,
//                new ModelMapper()
//        );
//    }
//
//    @Test
//    void testPurchasePackage_Success() {
//        String customerNickname = "pepe";
//        String packageName = "Vacaciones 2025";
//
//        // Mockeamos que existe un CustomerDTO
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setNickname(customerNickname);
//        when(userService.getCustomerDetailsByNickname(customerNickname)).thenReturn(customerDTO);
//
//        // Mockeamos que existe un paquete válido con todos los campos requeridos
//        FlightRoutePackageDTO pkgDTO = new FlightRoutePackageDTO();
//        pkgDTO.setName(packageName);
//        pkgDTO.setCreationDate(LocalDate.now());
//        pkgDTO.setValidityPeriodDays(30);
//        pkgDTO.setSeatType(EnumTipoAsiento.TURISTA);
//        when(flightRoutePackageService.getFlightRoutePackageByName(packageName)).thenReturn(pkgDTO);
//
//        // Mockeamos que NO existe previamente
//        when(repository.existsByCustomerAndPackage(customerNickname, packageName)).thenReturn(false);
//
//        // Ejecutamos
//        assertDoesNotThrow(() -> packagePurchaseService.purchasePackage(customerNickname, packageName));
//
//        // Verificamos que se haya llamado a save
//        verify(repository, times(1)).save(any(PackagePurchase.class));
//    }
//
//    @Test
//    void testPurchasePackage_AlreadyPurchased() {
//        String customerNickname = "pepe";
//        String packageName = "Vacaciones 2025";
//
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setNickname(customerNickname);
//        when(userService.getCustomerDetailsByNickname(customerNickname)).thenReturn(customerDTO);
//
//        FlightRoutePackageDTO pkgDTO = new FlightRoutePackageDTO();
//        pkgDTO.setName(packageName);
//        pkgDTO.setCreationDate(LocalDate.now());
//        pkgDTO.setValidityPeriodDays(30);
//        pkgDTO.setSeatType(EnumTipoAsiento.TURISTA);
//        when(flightRoutePackageService.getFlightRoutePackageByName(packageName)).thenReturn(pkgDTO);
//
//        // Ya existe
//        when(repository.existsByCustomerAndPackage(customerNickname, packageName)).thenReturn(true);
//
//        // Debe lanzar excepción
//        Exception ex = assertThrows(IllegalArgumentException.class,
//                () -> packagePurchaseService.purchasePackage(customerNickname, packageName));
//
//        assertTrue(ex.getMessage().toLowerCase().contains("ya compró"));
//        verify(repository, never()).save(any());
//    }
//}
