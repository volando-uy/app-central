package domain.services.flightroutepackage;

import app.DBConnection;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.mapper.UserMapper;
import domain.services.category.CategoryService;
import domain.services.city.CityService;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRoutePackageServiceTest {

    private IFlightRoutePackageService packageService;
    private IFlightRouteService flightRouteService; // Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        modelMapper = new ModelMapper();

        var userService     = new UserService(modelMapper, new UserMapper(modelMapper));
        var cityService     = new CityService(modelMapper);
        var categoryService = new CategoryService(modelMapper);
        flightRouteService  = new FlightRouteService(modelMapper, categoryService, userService, cityService);
        packageService      = new FlightRoutePackageService(flightRouteService, modelMapper);

        // ---- Semilla mínima en DB: Airline + Cities + Category ----
        try (EntityManager em = DBConnection.getEntityManager()) {
            em.getTransaction().begin();

            // Airline "air123"
            Airline airline = new Airline();
            airline.setNickname("air123");
            airline.setName("Test Airline");
            airline.setMail("test@mail.com");
            airline.setDescription("Aerolínea test");
            airline.setWeb("www.testair.com");
            em.persist(airline);

            // Cities (completar TODOS los campos con constraints)
            City mvd = new City("Montevideo", "UY", -34.9011, -56.1645);
            City bue = new City("Buenos Aires", "AR", -34.6037, -58.3816);
            em.persist(mvd);
            em.persist(bue);

            // Category "Promo"
            Category promo = new Category();
            promo.setName("Promo");
            em.persist(promo);

            em.getTransaction().commit();
        }

        // ---- Ahora sí, crear la FlightRoute real ----
        FlightRouteDTO dto = new FlightRouteDTO();
        dto.setName("Ruta A");
        dto.setDescription("Ruta directa");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(100.0);
        dto.setPriceBusinessClass(200.0);
        dto.setPriceExtraUnitBaggage(20.0);
        dto.setOriginCityName("Montevideo");
        dto.setDestinationCityName("Buenos Aires");
        dto.setAirlineNickname("air123");
        dto.setCategories(List.of("Promo"));

        flightRouteService.createFlightRoute(dto);
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
        assertEquals(String.format(ErrorMessages.ERR_PACKAGE_ALREADY_EXISTS, "Pack B"), ex.getMessage());
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
    //org.opentest4j.AssertionFailedError: Unexpected exception thrown: jakarta.persistence.EntityNotFoundException: Unable to find domain.models.flightRoute.FlightRoute with id Ruta A
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
        assertEquals(String.format(ErrorMessages.ERR_QUANTITY_MUST_BE_GREATER_THAN_ZERO), ex.getMessage());
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
