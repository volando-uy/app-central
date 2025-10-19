package domain.services.flightroute;

import app.DBConnection;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.enums.EnumEstatusRuta;
import domain.models.user.mapper.UserMapper;
import domain.services.airport.IAirportService;
import domain.services.category.CategoryService;
import domain.services.category.ICategoryService;
import domain.services.city.CityService;
import domain.services.city.ICityService;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ServiceFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightRouteServiceTest {

    private IFlightRouteService flightRouteService;
    private IUserService userService;
    private ICategoryService categoryService;
    private ICityService cityService;
    private IAirportService airportService;


    void fillDB() {
        userService.registerAirline(
                new BaseAirlineDTO("air123", "Test Airline", "test@air.com", "password", null, "Aerolínea de prueba", "https://testair.com"),
                null
        );

        categoryService.createCategory(new CategoryDTO("Promo"));

        cityService.createCity(new CityDTO("Montevideo", "Uruguay", -34.9, -56.2));
        cityService.createCity(new CityDTO("Buenos Aires", "Argentina", -34.6, -58.4));

        airportService.createAirport(
                new BaseAirportDTO("Aero Montevideo", "MON"),
                "Montevideo"
        );

        airportService.createAirport(
                new BaseAirportDTO("Aero Buenos Aires", "BAA"),
                "Buenos Aires"
        );
    }

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        // Instanciamos los servicios reales (en memoria)
        userService = ServiceFactory.getUserService();
        categoryService = ServiceFactory.getCategoryService(); // debería tener lista interna de categorías
        cityService = ServiceFactory.getCityService();         // debería tener lista interna de ciudades
        flightRouteService = ServiceFactory.getFlightRouteService();
        airportService = ServiceFactory.getAirportService();
        this.fillDB();
    }

    @Test
    @DisplayName("GIVEN valid DTO WHEN createFlightRoute THEN it's stored properly")
    void createFlightRoute_shouldStoreSuccessfully() {
        // GIVEN
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("Ruta MVD-BUE");
        dto.setDescription("Ruta directa");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(100.0);
        dto.setPriceBusinessClass(200.0);
        dto.setPriceExtraUnitBaggage(20.0);

        // WHEN
        BaseFlightRouteDTO created = flightRouteService.createFlightRoute(
                dto,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // THEN
        assertNotNull(created);
        assertEquals("Ruta MVD-BUE", created.getName());
        assertEquals("Ruta directa", created.getDescription());
        assertEquals(EnumEstatusRuta.SIN_ESTADO, created.getStatus());
        assertEquals(100.0, created.getPriceTouristClass());
    }

    @Test
    @DisplayName("GIVEN duplicated route name WHEN createFlightRoute THEN throw exception")
    void createFlightRoute_shouldRejectDuplicates() {
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("Duplicada");
        dto.setDescription("Una ruta duplicada");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(150.0);
        dto.setPriceBusinessClass(300.0);
        dto.setPriceExtraUnitBaggage(25.0);

        flightRouteService.createFlightRoute(
                dto,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightRouteService.createFlightRoute(dto, "MON", "BAA", "air123", List.of("Promo"), null);
        });

        assertEquals("Ya existe una ruta con el nombre: Duplicada", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN existing route WHEN getFlightRouteByName THEN return model")
    void getFlightRouteByName_shouldReturnRoute() {
        // GIVEN
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("RutaX");
        dto.setDescription("Desc");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(100.0);
        dto.setPriceBusinessClass(200.0);
        dto.setPriceExtraUnitBaggage(20.0);

        flightRouteService.createFlightRoute(
                dto,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // WHEN
        var route = flightRouteService.getFlightRouteByName("RutaX", true);

        // THEN
        assertNotNull(route);
        assertEquals("RutaX", route.getName());
    }

    @Test
    @DisplayName("GIVEN nonexistent route WHEN getFlightRouteByName THEN throw exception")
    void getFlightRouteByName_shouldThrowIfNotFound() {
        // WHEN + THEN
        var ex = assertThrows(NoResultException.class, () -> {
            flightRouteService.getFlightRouteByName("Inexistente", true);
        });
        assertTrue(ex.getMessage().contains("No result found for query"));
    }

    @Test
    @DisplayName("GIVEN existing route WHEN getFlightRouteDetailsByName THEN return DTO")
    void getFlightRouteDetailsByName_shouldReturnDTO() {
        // GIVEN
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("RutaDetalle");
        dto.setDescription("Detalles");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(120.0);
        dto.setPriceBusinessClass(250.0);
        dto.setPriceExtraUnitBaggage(30.0);

        flightRouteService.createFlightRoute(
                dto,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // WHEN
        FlightRouteDTO found = flightRouteService.getFlightRouteDetailsByName("RutaDetalle", true);

        // THEN
        assertNotNull(found);
        assertEquals("RutaDetalle", found.getName());
        assertEquals("MON", found.getOriginAeroCode());
        assertEquals("BAA", found.getDestinationAeroCode());
        assertEquals(List.of("Promo"), found.getCategoriesNames());
    }

    @Test
    @DisplayName("GIVEN nonexistent route WHEN getFlightRouteDetailsByName THEN throw exception")
    void getFlightRouteDetailsByName_shouldThrowIfNotFound() {
        // WHEN + THEN
        var ex = assertThrows(NoResultException.class, () -> {
            flightRouteService.getFlightRouteDetailsByName("Inexistente", true);
        });

        assertTrue(ex.getMessage().contains("No result found for query"));
    }

    @Test
    @DisplayName("GIVEN route created WHEN existFlightRoute THEN return true")
    void existFlightRoute_shouldReturnTrueIfExists() {
        // GIVEN
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("RutaExistente");
        dto.setDescription("Desc");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(90.0);
        dto.setPriceBusinessClass(190.0);
        dto.setPriceExtraUnitBaggage(15.0);

        flightRouteService.createFlightRoute(
                dto,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // WHEN + THEN
        assertTrue(flightRouteService.existFlightRoute("RutaExistente"));
        assertFalse(flightRouteService.existFlightRoute("NoExiste"));
    }

    @Test
    @DisplayName("GIVEN multiple routes WHEN getAllFlightRoutesDetailsByAirlineNickname THEN return correct list")
    void getAllRoutesByAirline_shouldReturnCorrectData() {
        BaseFlightRouteDTO dto1 = new BaseFlightRouteDTO();
        dto1.setName("Ruta1");
        dto1.setDescription("Desc1");
        dto1.setCreatedAt(LocalDate.now());
        dto1.setPriceTouristClass(80.0);
        dto1.setPriceBusinessClass(160.0);
        dto1.setPriceExtraUnitBaggage(10.0);

        BaseFlightRouteDTO dto2 = new BaseFlightRouteDTO();
        dto2.setName("Ruta2");
        dto2.setDescription("Desc2");
        dto2.setCreatedAt(LocalDate.now());
        dto2.setPriceTouristClass(150.0);
        dto2.setPriceBusinessClass(300.0);
        dto2.setPriceExtraUnitBaggage(40.0);

        flightRouteService.createFlightRoute(
                dto1,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );
        flightRouteService.createFlightRoute(
                dto2,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // WHEN
        List<FlightRouteDTO> routes = flightRouteService.getFlightRoutesDetailsByAirlineNickname("air123", true);

        // THEN
        assertEquals(2, routes.size());
        assertEquals("Ruta1", routes.get(0).getName());
        assertEquals("Ruta2", routes.get(1).getName());
    }

    @Test
    @DisplayName("GIVEN a valid route without status WHEN accepting the route THEN route status should be accepted")
    void confirmRouteStatus_shouldUpdateRouteCorrectly() {
        BaseFlightRouteDTO dto1 = new BaseFlightRouteDTO();
        dto1.setName("Ruta1");
        dto1.setDescription("Desc1");
        dto1.setCreatedAt(LocalDate.now());
        dto1.setPriceTouristClass(80.0);
        dto1.setPriceBusinessClass(160.0);
        dto1.setPriceExtraUnitBaggage(10.0);

        // GIVEN
        flightRouteService.createFlightRoute(
                dto1,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // WHEN
        flightRouteService.setStatusFlightRouteByName("Ruta1", true);

        // THEN
        BaseFlightRouteDTO route = flightRouteService.getFlightRouteDetailsByName("Ruta1", false);

        assertEquals("Ruta1", route.getName());
        assertEquals(EnumEstatusRuta.CONFIRMADA, route.getStatus());
    }

    @Test
    @DisplayName("GIVEN a valid route without status WHEN rejecting the route THEN route status should be accepted")
    void rejectRouteStatus_shouldUpdateRouteCorrectly() {
        BaseFlightRouteDTO dto1 = new BaseFlightRouteDTO();
        dto1.setName("Ruta1");
        dto1.setDescription("Desc1");
        dto1.setCreatedAt(LocalDate.now());
        dto1.setPriceTouristClass(80.0);
        dto1.setPriceBusinessClass(160.0);
        dto1.setPriceExtraUnitBaggage(10.0);

        // GIVEN
        flightRouteService.createFlightRoute(
                dto1,
                "MON",
                "BAA",
                "air123",
                List.of("Promo"),
                null
        );

        // WHEN
        flightRouteService.setStatusFlightRouteByName("Ruta1", false);

        // THEN
        BaseFlightRouteDTO route = flightRouteService.getFlightRouteDetailsByName("Ruta1", false);

        assertEquals("Ruta1", route.getName());
        assertEquals(EnumEstatusRuta.RECHAZADA, route.getStatus());
    }
}