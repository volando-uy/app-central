package domain.services.flightroute;

import app.DBConnection;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.user.mapper.UserMapper;
import domain.services.category.CategoryService;
import domain.services.category.ICategoryService;
import domain.services.city.CityService;
import domain.services.city.ICityService;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
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


    void fillDB() {
        // Registramos datos base
        userService.registerAirline(new AirlineDTO(
                "air123", "Test Airline", "test@air.com", "Aerolínea de prueba", "https://testair.com"
        ));

        categoryService.createCategory(new CategoryDTO("Promo"));

        cityService.createCity(new CityDTO("Montevideo", "Uruguay", -34.9, -56.2, new ArrayList<>()));
        cityService.createCity(new CityDTO("Buenos Aires", "Argentina", -34.6, -58.4, new ArrayList<>()));
    }

    @BeforeAll
    void setUp() {
        TestUtils.cleanDB();
        ModelMapper modelMapper = new ModelMapper();

        // Instanciamos los servicios reales (en memoria)
        userService = new UserService(modelMapper, new UserMapper(modelMapper));
        categoryService = new CategoryService(modelMapper); // debería tener lista interna de categorías
        cityService = new CityService(modelMapper);         // debería tener lista interna de ciudades

        // Creamos el servicio a testear con sus dependencias reales
        flightRouteService = new FlightRouteService(modelMapper);
        this.fillDB();
    }

    @Test
    @DisplayName("GIVEN valid DTO WHEN createFlightRoute THEN it's stored properly")
    void createFlightRoute_shouldStoreSuccessfully() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO();
        dto.setName("Ruta MVD-BUE");
        dto.setDescription("Ruta directa");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(100.0);
        dto.setPriceBusinessClass(200.0);
        dto.setPriceExtraUnitBaggage(20.0);
        dto.setOriginCityName("Montevideo");
        dto.setDestinationCityName("Buenos Aires");
        dto.setAirlineNickname("air123");
        dto.setCategories(List.of("Promo"));

        // WHEN
        //Preguntar si existe la aereolinea
        boolean existsAirline = userService.existsUserByNickname(dto.getAirlineNickname());
        assertTrue(existsAirline);

        FlightRouteDTO created = flightRouteService.createFlightRoute(dto);

        // THEN
        assertNotNull(created);
        assertEquals("Ruta MVD-BUE", created.getName());
        assertEquals("Montevideo", created.getOriginCityName());
        assertEquals("Buenos Aires", created.getDestinationCityName());
        assertEquals(List.of("Promo"), created.getCategories());
    }

    @Test
    @DisplayName("GIVEN duplicated route name WHEN createFlightRoute THEN throw exception")
    void createFlightRoute_shouldRejectDuplicates() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO();
        dto.setName("Duplicada");
        dto.setDescription("Una ruta duplicada");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(150.0);
        dto.setPriceBusinessClass(300.0);
        dto.setPriceExtraUnitBaggage(25.0);
        dto.setOriginCityName("Montevideo");
        dto.setDestinationCityName("Buenos Aires");
        dto.setAirlineNickname("air123");
        dto.setCategories(List.of("Promo"));

        flightRouteService.createFlightRoute(dto);

        // WHEN + THEN
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightRouteService.createFlightRoute(dto);
        });

        assertEquals("Ya existe una ruta con el nombre: Duplicada", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN existing route WHEN getFlightRouteByName THEN return model")
    void getFlightRouteByName_shouldReturnRoute() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO(
                "RutaX", "Desc", LocalDate.now(),
                100.0, 200.0, 20.0,
                "Montevideo", "Buenos Aires", "air123",
                List.of("Promo"),
                List.of("Vuelo1")
        );
        flightRouteService.createFlightRoute(dto);

        // WHEN
        var route = flightRouteService.getFlightRouteByName("RutaX");

        // THEN
        assertNotNull(route);
        assertEquals("RutaX", route.getName());
    }

    @Test
    @DisplayName("GIVEN nonexistent route WHEN getFlightRouteByName THEN throw exception")
    void getFlightRouteByName_shouldThrowIfNotFound() {
        // WHEN + THEN
        var ex = assertThrows(NoResultException.class, () -> {
            flightRouteService.getFlightRouteByName("Inexistente");
        });
        assertTrue(ex.getMessage().contains("No result found for query"));
    }

    @Test
    @DisplayName("GIVEN existing route WHEN getFlightRouteDetailsByName THEN return DTO")
    void getFlightRouteDetailsByName_shouldReturnDTO() {
        // GIVEN
        //Crear vuelo nombre vuelo1

//        flightRouteService.createFlightRoute(new FlightRouteDTO(
//                "RutaDetalle", "Detalles", LocalDate.now(),
//                120.0, 250.0, 30.0,
//                "Montevideo", "Buenos Aires", "air123",
//                List.of("Promo"),
//                List.of("Vuelo1")
//        ));
        FlightRouteDTO dto = new FlightRouteDTO(
                "RutaDetalle", "Detalles", LocalDate.now(),
                120.0, 250.0, 30.0,
                "Montevideo", "Buenos Aires", "air123",
                List.of("Promo"),
                List.of("Vuelo1")
        );
        flightRouteService.createFlightRoute(dto);

        // WHEN
        FlightRouteDTO found = flightRouteService.getFlightRouteDetailsByName("RutaDetalle");

        // THEN
        assertNotNull(found);
        assertEquals("RutaDetalle", found.getName());
        assertEquals("Montevideo", found.getOriginCityName());
        assertEquals("Buenos Aires", found.getDestinationCityName());
        assertEquals(List.of("Promo"), found.getCategories());
    }

    @Test
    @DisplayName("GIVEN nonexistent route WHEN getFlightRouteDetailsByName THEN throw exception")
    void getFlightRouteDetailsByName_shouldThrowIfNotFound() {
        // WHEN + THEN
        var ex = assertThrows(NoResultException.class, () -> {
            flightRouteService.getFlightRouteDetailsByName("Inexistente");
        });

        assertTrue(ex.getMessage().contains("No result found for query"));
    }

    @Test
    @DisplayName("GIVEN route created WHEN existFlightRoute THEN return true")
    void existFlightRoute_shouldReturnTrueIfExists() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO(
                "RutaExistente", "Desc", LocalDate.now(),
                90.0, 190.0, 15.0,
                "Montevideo", "Buenos Aires", "air123",
                List.of("Promo"),
                List.of("Vuelo1")
        );
        flightRouteService.createFlightRoute(dto);

        // WHEN + THEN
        assertTrue(flightRouteService.existFlightRoute("RutaExistente"));
        assertFalse(flightRouteService.existFlightRoute("NoExiste"));
    }

    @Test
    @DisplayName("GIVEN multiple routes WHEN getAllFlightRoutesDetailsByAirlineNickname THEN return correct list")
    void getAllRoutesByAirline_shouldReturnCorrectData() {
        TestUtils.cleanDB();
        this.fillDB();
        // GIVEN
        flightRouteService.createFlightRoute(new FlightRouteDTO(
                "Ruta1", "Desc1", LocalDate.now(),
                80.0, 160.0, 10.0,
                "Montevideo", "Buenos Aires", "air123",
                List.of("Promo"),
                List.of("Vuelo1")
        ));

        flightRouteService.createFlightRoute(new FlightRouteDTO(
                "Ruta2", "Desc2", LocalDate.now(),
                150.0, 300.0, 40.0,
                "Montevideo", "Buenos Aires", "air123",
                List.of("Promo"),
                List.of("Vuelo2")
        ));

        // WHEN
        List<FlightRouteDTO> routes = flightRouteService.getAllFlightRoutesDetailsByAirlineNickname("air123");

        // THEN
        assertEquals(2, routes.size());
        assertEquals("Ruta1", routes.get(0).getName());
        assertEquals("Ruta2", routes.get(1).getName());
    }

    @Test
    @DisplayName("GIVEN route with no flights WHEN getFlightsByRouteName THEN return empty list")
    void getFlightsByRouteName_shouldReturnEmptyListIfNoFlights() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO(
                "RutaSinVuelos", "Desc", LocalDate.now(),
                110.0, 220.0, 35.0,
                "Montevideo", "Buenos Aires", "air123",
                List.of("Promo"),
                List.of("VueloA")
        );
        flightRouteService.createFlightRoute(dto);
        // WHEN
        List<FlightDTO> flights = flightRouteService.getFlightsByRouteName("RutaSinVuelos");
        // THEN
        assertNotNull(flights);
        assertTrue(flights.isEmpty());
    }

}
