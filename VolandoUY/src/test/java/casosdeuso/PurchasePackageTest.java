package casosdeuso;

import controllers.packagePurchase.IPackagePurchaseController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.packagePurchase.PackagePurchaseDTO;
import domain.dtos.user.CustomerDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PurchasePackageTest {

    private IUserController userController;
    private IFlightRoutePackageController packageController;
    private IPackagePurchaseController purchaseController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        userController = ControllerFactory.getUserController();
        packageController = ControllerFactory.getFlightRoutePackageController();
        purchaseController = ControllerFactory.getPackagePurchaseController();

        // Crear un cliente
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("pepe01");
        customer.setName("Pepe");
        customer.setMail("pepe@gmail.com");
        userController.registerCustomer(customer);

        // Crear un paquete
        FlightRoutePackageDTO packageDTO = new FlightRoutePackageDTO();
        packageDTO.setName("Promo Verano");
        packageDTO.setCost(500.0);
        packageDTO.setExpirationDate(LocalDate.of(2026, 1, 1));
        packageController.createFlightRoutePackage(packageDTO);
    }

    /**
     * Caso de uso: Compra de Paquete.
     * El administrador selecciona un paquete, selecciona un cliente,
     * y el sistema registra la compra.
     */
    @Test
    void purchasePackageTest() {
        // Listar paquetes existentes
        assertFalse(packageController.getAllFlightRoutePackages().isEmpty());
        String packageName = packageController.getAllFlightRoutePackages().get(0).getName();
        assertEquals("Promo Verano", packageName);

        // Listar clientes
        assertFalse(userController.getAllCustomers().isEmpty());
        String customerNickname = userController.getAllCustomers().get(0).getNickname();
        assertEquals("pepe01", customerNickname);

        // Registrar compra
        purchaseController.purchasePackage(customerNickname, packageName);

        // Verificar que se registró la compra
        PackagePurchaseDTO purchase = purchaseController.getAllPurchases().get(0);
        assertNotNull(purchase);
        assertEquals("pepe01", purchase.getCustomer().getNickname());
        assertEquals("Promo Verano", purchase.getFlightRoutePackage().getName());
    }

    @Test
    void purchasePackageTwiceShouldFail() {
        String customerNickname = "pepe01";
        String packageName = "Promo Verano";

        // Primera compra OK
        purchaseController.purchasePackage(customerNickname, packageName);

        // Segunda compra debería fallar
        assertThrows(IllegalArgumentException.class, () ->
                purchaseController.purchasePackage(customerNickname, packageName)
        );
    }
}
