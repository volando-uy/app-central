package casosdeuso;

import controllers.flightRoutePackage.IFlightRoutePackageController;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.enums.EnumTipoAsiento;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterFlightRoutePackageTest {

    private IFlightRoutePackageController flightRoutePackageController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        flightRoutePackageController = ControllerFactory.getFlightRoutePackageController();
    }

    @Test
    @DisplayName("CU: Alta de paquete de rutas de vuelo exitoso")
    void altaDePaquete_exitoso() {
        // Paso 1: Crear DTO válido
//        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
//        dto.setName("Promo Sudamérica");
//        dto.setDescription("Descuento para vuelos dentro de Sudamérica");
//        dto.setValidityPeriodDays(30);
//        dto.setDiscount(10.0);
//        dto.setCreationDate(LocalDate.now());
//        dto.setSeatType(EnumTipoAsiento.TURISTA);

        BaseFlightRoutePackageDTO baseFlightRoutePackageDTO = new BaseFlightRoutePackageDTO();
        baseFlightRoutePackageDTO.setName("Promo Sudamérica");
        baseFlightRoutePackageDTO.setDescription("Descuento para vuelos dentro de Sudamérica");
        baseFlightRoutePackageDTO.setValidityPeriodDays(30);
        baseFlightRoutePackageDTO.setDiscount(10.0);
        baseFlightRoutePackageDTO.setCreationDate(LocalDate.now());
        baseFlightRoutePackageDTO.setSeatType(EnumTipoAsiento.TURISTA);
        baseFlightRoutePackageDTO.setTotalPrice(500.0);

        // Paso 2: Crear el paquete
        BaseFlightRoutePackageDTO creado = flightRoutePackageController.createFlightRoutePackage(baseFlightRoutePackageDTO);

        // Paso 3: Validaciones
        assertNotNull(creado);
        assertEquals("Promo Sudamérica", creado.getName());
        assertEquals(30, creado.getValidityPeriodDays());
        assertEquals(10.0, creado.getDiscount());
        assertEquals(EnumTipoAsiento.TURISTA, creado.getSeatType());

        // Paso 4: Intentar duplicado
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightRoutePackageController.createFlightRoutePackage(baseFlightRoutePackageDTO);
        });

        assertEquals(String.format(ErrorMessages.ERR_PACKAGE_ALREADY_EXISTS, baseFlightRoutePackageDTO.getName()), ex.getMessage());
    }
}
