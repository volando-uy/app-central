package controllers.flightRoutePackage;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.enums.EnumTipoAsiento;
import factory.ControllerFactory;
import factory.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightRoutePackageControllerTest {

    private IFlightRoutePackageController packageController;
    private IUserController userController;
    private IFlightRouteController flightRouteController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        this.packageController = ControllerFactory.getFlightRoutePackageController();
        this.userController = ControllerFactory.getUserController();
        this.flightRouteController = ControllerFactory.getFlightRouteController();
    }

    @Test
    @DisplayName("Debe crear el paquete de ruta de vuelo correctamente")
    void createFlightRoutePackage_shouldCreateCorrectly() {
        // GIVEN
        BaseFlightRoutePackageDTO dto = new BaseFlightRoutePackageDTO(
                "Promo Verano", "Descuento especial", 30, 10.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA, 100.0
        );

        // WHEN
        BaseFlightRoutePackageDTO result = packageController.createFlightRoutePackage(dto);

        // THEN
        assertNotNull(result);
        assertEquals("Promo Verano", result.getName());
    }

    @Test
    @DisplayName("Debe retornar el paquete por nombre desde el controller")
    void getFlightRoutePackageByName_shouldReturnFromController() {
        // GIVEN
        BaseFlightRoutePackageDTO dto = new BaseFlightRoutePackageDTO(
                "Promo Verano", "Descripción", 30, 5.0,
                LocalDate.now(), EnumTipoAsiento.EJECUTIVO, 100.0
        );
        packageController.createFlightRoutePackage(dto);

        // WHEN
        FlightRoutePackageDTO result = packageController.getFlightRoutePackageDetailsByName("Promo Verano");

        // THEN
        assertNotNull(result);
        assertEquals("Promo Verano", result.getName());
        assertEquals(EnumTipoAsiento.EJECUTIVO, result.getSeatType());
    }

    @Test
    @DisplayName("Debe retornar nombres de todos los paquetes no comprados")
    void getAllNotBoughtFlightRoutePackagesNames_shouldReturnListFromController() {
        // GIVEN
        packageController.createFlightRoutePackage(new BaseFlightRoutePackageDTO(
                "Pack 1", "desc 1", 10, 5.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 100.0
        ));
        packageController.createFlightRoutePackage(new BaseFlightRoutePackageDTO(
                "Pack 2", "desc 2", 15, 7.5, LocalDate.now(), EnumTipoAsiento.EJECUTIVO, 100.0
        ));

        // WHEN
        List<String> result = packageController.getAllNotBoughtFlightRoutesPackagesNames();

        // THEN
        assertEquals(2, result.size());
        assertTrue(result.contains("Pack 1"));
        assertTrue(result.contains("Pack 2"));
    }

    @Test
    @DisplayName("Debe agregar ruta de vuelo a un paquete vía el controller")
    void addFlightRouteToPackage_shouldAddCorrectly() {
        // GIVEN: Crear paquete y ruta
        packageController.createFlightRoutePackage(new BaseFlightRoutePackageDTO(
                "Pack Ruta A", "Paquete con ruta", 10, 5.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 100.0
        ));

        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("Airline1");
        airlineDTO.setName("name");
        airlineDTO.setMail("agqmi@gmail.com");
        airlineDTO.setDescription("descriptionasdasd");
        airlineDTO.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO, null);

        // Crear la ruta de vuelo
        BaseFlightRouteDTO flightRouteDTO = new BaseFlightRouteDTO();
        flightRouteDTO.setName("Ruta A");
        flightRouteDTO.setDescription("Descripción de Ruta A");
        flightRouteDTO.setCreatedAt(LocalDate.now());
        flightRouteDTO.setPriceTouristClass(150.0);
        flightRouteDTO.setPriceBusinessClass(300.0);
        flightRouteDTO.setPriceExtraUnitBaggage(50.0);
//        flightRouteController.createFlightRoute(flightRouteDTO, "Montevideo", "Buenos Aires", "Airline1", new ArrayList<>());

        // WHEN
        packageController.addFlightRouteToPackage("Pack Ruta A", "Ruta A", 2);

        // THEN
        FlightRoutePackageDTO result = packageController.getFlightRoutePackageDetailsByName("Pack Ruta A");
        assertNotNull(result);
        assertTrue(result.getFlightRouteNames().contains("Ruta A"));
    }

    @Test
    @DisplayName("Debe verificar existencia de paquete de ruta")
    void flightRoutePackageExists_shouldReturnCorrectly() {
        // GIVEN
        packageController.createFlightRoutePackage(new BaseFlightRoutePackageDTO(
                "Promo Check", "desc", 10, 5.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 100.0
        ));

        // WHEN
        boolean exists = packageController.flightRoutePackageExists("Promo Check");

        // THEN
        assertTrue(exists);
    }
}
