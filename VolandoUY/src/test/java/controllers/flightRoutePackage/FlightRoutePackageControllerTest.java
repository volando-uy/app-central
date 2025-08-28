package controllers.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRoutePackageControllerTest {

    private IFlightRoutePackageService packageService;
    private IFlightRoutePackageController packageController;

    @BeforeEach
    void setUp() {
        packageService = mock(IFlightRoutePackageService.class);
        packageController = new FlightRoutePackageController(packageService);
    }

    @Test
    @DisplayName("Debe delegar la creación del paquete de ruta de vuelo al servicio")
    void createFlightRoutePackage_shouldCallService() {
        // GIVEN
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO("Promo Verano", "Descuento especial", 30, 10.0, LocalDate.now(), EnumTipoAsiento.TURISTA, new ArrayList<>());

        when(packageService.createFlightRoutePackage(dto)).thenReturn(dto);

        // WHEN
        FlightRoutePackageDTO result = packageController.createFlightRoutePackage(dto);

        // THEN
        assertNotNull(result);
        assertEquals("Promo Verano", result.getName());
        verify(packageService).createFlightRoutePackage(dto);
    }

    @Test
    @DisplayName("Debe retornar el paquete por nombre desde el servicio")
    void getFlightRoutePackageByName_shouldReturnFromService() {
        // GIVEN
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO("Promo Verano", "Descripción", 30, 5.0, LocalDate.now(), EnumTipoAsiento.EJECUTIVO, new ArrayList<>());

        when(packageService.getFlightRoutePackageByName("Promo Verano")).thenReturn(dto);

        // WHEN
        FlightRoutePackageDTO result = packageController.getFlightRoutePackageByName("Promo Verano");

        // THEN
        assertNotNull(result);
        assertEquals("Promo Verano", result.getName());
    }

    @Test
    @DisplayName("Debe retornar nombres de todos los paquetes no comprados")
    void getAllNotBoughtFlightRoutePackagesNames_shouldReturnListFromService() {
        // GIVEN
        List<String> mockList = List.of("Pack 1", "Pack 2");
        when(packageService.getAllNotBoughtFlightRoutePackagesNames()).thenReturn(mockList);

        // WHEN
        List<String> result = packageController.getAllNotBoughtFlightRoutePackagesNames();

        // THEN
        assertEquals(2, result.size());
        assertEquals("Pack 1", result.get(0));
        verify(packageService).getAllNotBoughtFlightRoutePackagesNames();
    }

    @Test
    @DisplayName("Debe agregar ruta de vuelo a un paquete vía el servicio")
    void addFlightRouteToPackage_shouldCallService() {
        // WHEN
        packageController.addFlightRouteToPackage("Pack A", "Ruta A", 3);

        // THEN
        verify(packageService).addFlightRouteToPackage("Pack A", "Ruta A", 3);
    }

    @Test
    @DisplayName("Debe verificar existencia de paquete de ruta")
    void flightRoutePackageExists_shouldReturnCorrectly() {
        // GIVEN
        when(packageService.flightRoutePackageExists("Promo Verano")).thenReturn(true);

        // WHEN
        boolean exists = packageController.flightRoutePackageExists("Promo Verano");

        // THEN
        assertTrue(exists);
        verify(packageService).flightRoutePackageExists("Promo Verano");
    }
}
