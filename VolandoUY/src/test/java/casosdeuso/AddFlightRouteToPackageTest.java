package casosdeuso;

import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
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

        ServiceFactory.getCityService().createCity(new BaseCityDTO("Montevideo", "Uruguay", -34.9011, -56.1645));
        ServiceFactory.getCityService().createCity(new BaseCityDTO("Asunción", "Paraguay", -25.2637, -57.5759));


        // Crear aerolínea
//        userController.registerAirline(new AirlineDTO(
//                "uyair", "Uruguay Airlines", "uy@mail.com", "Aerolínea uruguaya", "www.uyair.com"
//        ));

        // Crear ruta de vuelo
        BaseFlightRouteDTO flightRouteDTO = new BaseFlightRouteDTO();
        flightRouteDTO.setName("UY-ASU");
        flightRouteDTO.setDescription("Ruta Montevideo - Asunción");
        flightRouteDTO.setCreatedAt(LocalDate.now());
        flightRouteDTO.setPriceTouristClass(200.0);
        flightRouteDTO.setPriceBusinessClass(350.0);
        flightRouteDTO.setPriceExtraUnitBaggage(50.0);
//        flightRouteController.createFlightRoute(flightRouteDTO, "Montevideo", "Asunción", "uyair", List.of());

        BaseFlightRoutePackageDTO packageDTO = new BaseFlightRoutePackageDTO();
        packageDTO.setName("Promo Paraguay");
        packageDTO.setDescription("Paquete con descuento para Paraguay");
        packageDTO.setValidityPeriodDays(15);
        packageDTO.setTotalPrice(20.0);
        packageDTO.setCreationDate(LocalDate.now());
        packageDTO.setSeatType(EnumTipoAsiento.TURISTA);
        packageController.createFlightRoutePackage(packageDTO);
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
