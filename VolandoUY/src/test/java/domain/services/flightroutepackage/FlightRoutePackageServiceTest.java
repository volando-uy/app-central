package domain.services.flightroutepackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRoutePackageServiceTest {

    private IFlightRoutePackageService packageService;
    private IFlightRouteService flightRouteService; // Mock
    private ModelMapper modelMapper;

    private FlightRoute dummyRoute;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        flightRouteService = mock(IFlightRouteService.class);
        packageService = new FlightRoutePackageService(flightRouteService, modelMapper);

        dummyRoute = new FlightRoute(
                "Ruta A",
                "Descripción A",
                LocalDate.now(),
                100.0,
                150.0,
                20.0
        );

        // Mock: retornar ruta existente al buscar por nombre
        when(flightRouteService.getFlightRouteByName("Ruta A")).thenReturn(dummyRoute);
    }

    @Test
    @DisplayName("GIVEN valid DTO WHEN createFlightRoutePackage THEN package is created")
    void createFlightRoutePackage_shouldCreatePackage() {
        // GIVEN
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO(
                "Pack A", "Descripción del paquete", 10, 15.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        );

        // WHEN
        FlightRoutePackageDTO created = packageService.createFlightRoutePackage(dto);

        // THEN
        assertEquals("Pack A", created.getName());
        assertTrue(packageService.flightRoutePackageExists("Pack A"));
    }

    @Test
    @DisplayName("GIVEN duplicate name WHEN createFlightRoutePackage THEN throw exception")
    void createFlightRoutePackage_shouldNotAllowDuplicates() {
        // GIVEN un paquete ya creado
        packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Pack B", "Otro paquete", 5, 10.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        ));

        // WHEN se intenta crear otro con el mismo nombre
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                    "Pack B", "Duplicado", 5, 10.0,
                    LocalDate.now(), EnumTipoAsiento.TURISTA
            ));
        });

        // THEN
        assertEquals("El paquete ya existe", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN existing package WHEN getFlightRoutePackageByName THEN return correct DTO")
    void getFlightRoutePackageByName_shouldReturnCorrectDTO() {
        // GIVEN un paquete creado
        packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Pack C", "Descripción C", 7, 5.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        ));

        // WHEN se busca por nombre
        var result = packageService.getFlightRoutePackageByName("Pack C");

        // THEN
        assertNotNull(result);
        assertEquals("Pack C", result.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing package WHEN getFlightRoutePackageByName THEN throw exception")
    void getFlightRoutePackageByName_shouldThrowIfNotFound() {
        // WHEN se busca uno inexistente
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            packageService.getFlightRoutePackageByName("Inexistente");
        });

        // THEN
        assertEquals("El paquete de ruta del vuelo con nombre Inexistente no fue encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN packages exist WHEN getAllNotBoughtFlightRoutePackagesNames THEN return names")
    void getAllNotBoughtFlightRoutePackagesNames_shouldReturnAllNames() {
        // GIVEN varios paquetes creados
        packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Pack D", "DD", 5, 10.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        ));

        packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Pack E", "EE", 5, 10.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        ));

        // WHEN se piden los nombres
        List<String> names = packageService.getAllNotBoughtFlightRoutePackagesNames();

        // THEN
        assertEquals(2, names.size());
        assertTrue(names.contains("Pack D"));
        assertTrue(names.contains("Pack E"));
    }

    @Test
    @DisplayName("GIVEN package and valid route WHEN addFlightRouteToPackage THEN route is added")
    void addFlightRouteToPackage_shouldAddRoute() {
        // GIVEN un paquete creado
        packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Pack F", "Paquete FF", 10, 15.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        ));

        // WHEN se agrega una ruta válida
        assertDoesNotThrow(() -> {
            packageService.addFlightRouteToPackage("Pack F", "Ruta A", 2);
        });
    }

    @Test
    @DisplayName("GIVEN invalid quantity WHEN addFlightRouteToPackage THEN throw exception")
    void addFlightRouteToPackage_shouldFailIfQuantityInvalid() {
        // GIVEN paquete válido
        packageService.createFlightRoutePackage(new FlightRoutePackageDTO(
                "Pack G", "GG", 10, 10.0,
                LocalDate.now(), EnumTipoAsiento.TURISTA
        ));

        // WHEN se pasa cantidad inválida
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            packageService.addFlightRouteToPackage("Pack G", "Ruta A", 0);
        });

        // THEN
        assertEquals("La cantidad debe ser mayor que cero.", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN non-existing package WHEN addFlightRouteToPackage THEN throw exception")
    void addFlightRouteToPackage_shouldFailIfPackageNotFound() {
        // WHEN se intenta agregar a un paquete inexistente
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            packageService.addFlightRouteToPackage("Inexistente", "Ruta A", 1);
        });

        // THEN
        assertEquals("El paquete de ruta del vuelo con nombre Inexistente no fue encontrado", ex.getMessage());
    }
}
