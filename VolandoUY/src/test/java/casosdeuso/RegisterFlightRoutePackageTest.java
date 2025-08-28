package casosdeuso;

import controllers.flightRoutePackage.IFlightRoutePackageController;
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
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
        dto.setName("Promo Sudamérica");
        dto.setDescription("Descuento para vuelos dentro de Sudamérica");
        dto.setValidityPeriodDays(30);
        dto.setDiscount(10.0);
        dto.setCreationDate(LocalDate.now());
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        // Paso 2: Crear el paquete
        FlightRoutePackageDTO creado = flightRoutePackageController.createFlightRoutePackage(dto);

        // Paso 3: Validaciones
        assertNotNull(creado);
        assertEquals("Promo Sudamérica", creado.getName());
        assertEquals(30, creado.getValidityPeriodDays());
        assertEquals(10.0, creado.getDiscount());
        assertEquals(EnumTipoAsiento.TURISTA, creado.getSeatType());

        // Paso 4: Intentar duplicado
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightRoutePackageController.createFlightRoutePackage(dto);
        });

        assertEquals(String.format(ErrorMessages.ERR_PACKAGE_ALREADY_EXISTS, dto.getName()), ex.getMessage());
    }
}
