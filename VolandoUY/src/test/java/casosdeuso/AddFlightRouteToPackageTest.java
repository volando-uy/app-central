package casosdeuso;

import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.models.enums.EnumTipoAsiento;
import factory.ControllerFactory;
import factory.ServiceFactory;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddFlightRouteToPackageTest {

    private IFlightRoutePackageController packageController;
    private IFlightRouteController flightRouteController;
    private IUserController userController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        packageController = ControllerFactory.getFlightRoutePackageController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        userController = ControllerFactory.getUserController();

        // Crear ciudades necesarias
        ServiceFactory.getCityService().createCity(new CityDTO("Montevideo", "Uruguay", -34.9011, -56.1645, List.of("Carrasco")));
        ServiceFactory.getCityService().createCity(new CityDTO("Asunción", "Paraguay", -25.2637, -57.5759, List.of("Silvio Pettirossi")));

        // Crear aerolínea
        userController.registerAirline(new AirlineDTO(
                "uyair", "Uruguay Airlines", "uy@mail.com", "Aerolínea uruguaya", "www.uyair.com"
        ));

        // Crear ruta de vuelo
        flightRouteController.createFlightRoute(new FlightRouteDTO(
                "UY-ASU",
                "Ruta Montevideo - Asunción",
                LocalDate.now(),
                200.0,
                350.0,
                50.0,
                "Montevideo",
                "Asunción",
                "uyair",
                List.of(),
                List.of()
        ));

        // Crear paquete
        packageController.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Promo Paraguay",
                "Paquete con descuento para Paraguay",
                15,
                20.0,
                LocalDate.now(),
                EnumTipoAsiento.TURISTA,
                List.of()
        ));
    }

    @Test
    @DisplayName("CU: Agregar ruta de vuelo a paquete existente exitosamente")
    void agregarRutaAVuelo_exitoso() {
        // Paso 1: Listar paquetes no comprados
        List<String> paquetes = packageController.getAllNotBoughtFlightRoutesPackagesNames();
        assertTrue(paquetes.contains("Promo Paraguay"));

        // Paso 2: Listar aerolíneas
        List<BaseAirlineDTO> aerolineas = userController.getAllAirlinesSimpleDetails();
        assertFalse(aerolineas.isEmpty());

        // Paso 3: Obtener rutas de aerolínea
        List<BaseFlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesSimpleDetailsByAirlineNickname("uyair");
        assertEquals(1, rutas.size());
        String rutaNombre = rutas.get(0).getName();

        // Paso 4: Agregar ruta al paquete
        packageController.addFlightRouteToPackage("Promo Paraguay", rutaNombre, 2);

        // Validar que no falla (pasa si no lanza excepción)
        var paquete = packageController.getFlightRoutePackageSimpleDetailsByName("Promo Paraguay");
        assertNotNull(paquete);
    }

    @Test
    @DisplayName("CU: Error al agregar ruta con cantidad inválida")
    void agregarRutaCantidadInvalida() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            packageController.addFlightRouteToPackage("Promo Paraguay", "UY-ASU", 0);
        });
        assertEquals(ErrorMessages.ERR_QUANTITY_MUST_BE_GREATER_THAN_ZERO, ex.getMessage());
    }

    @Test
    @DisplayName("CU: Error al agregar ruta a paquete inexistente")
    void agregarRutaAPaqueteInexistente() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            packageController.addFlightRouteToPackage("NO_EXISTE", "UY-ASU", 1);
        });
        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, "NO_EXISTE"), ex.getMessage());
    }

    @Test
    @DisplayName("CU: Error al agregar ruta inexistente a paquete")
    void agregarRutaInexistenteAPaquete() {
        Exception ex = assertThrows(NoResultException.class, () -> {
            packageController.addFlightRouteToPackage("Promo Paraguay", "NO_EXISTE", 1);
        });
        //No result found for query [SELECT fr FROM FlightRoute fr WHERE LOWER(fr.name) = LOWER(:name)]
        assertTrue(ex.getMessage().contains("No result found for query"));
    }
}
