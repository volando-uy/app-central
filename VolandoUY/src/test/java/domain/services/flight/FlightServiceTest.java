package domain.services.flight;

import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.flight.Flight;
import domain.services.city.ICityService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ServiceFactory;
import org.junit.jupiter.api.*;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {

    private IFlightService flightService;
    private IUserService userService;
    private IFlightRouteService flightRouteService;
    private ICityService cityService;


    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        userService = new UserService();
        flightService = ServiceFactory.getFlightService();
        flightRouteService = ServiceFactory.getFlightRouteService();
        cityService = ServiceFactory.getCityService();

        userService.registerAirline(new AirlineDTO(
                "air123", "Test Airline", "test@mail.com", "Una aerolínea de pruebaaaaaaa", "www.testair.com"
        ));

        // Crear ciudades
        ServiceFactory.getCityService().createCity(
                new CityDTO("Buenos Aires", "Argentina", -34.6037, -58.3816)
        );
        ServiceFactory.getCityService().createCity(
                new CityDTO("Madrid", "España", 40.4168, -3.7038)
        );

        // Crear ruta de vuelo
        FlightRouteDTO route = new FlightRouteDTO();
        route.setName("AAA");
        route.setDescription("Ruta de prueba");
        route.setCreatedAt(LocalDate.now());
        route.setPriceTouristClass(1000.0);
        route.setPriceBusinessClass(2000.0);
        route.setPriceExtraUnitBaggage(300.0);
        route.setOriginCityName("Buenos Aires");
        route.setDestinationCityName("Madrid");
        route.setAirlineNickname("air123");
        route.setCategories(List.of());

        flightRouteService.createFlightRoute(route, "Buenos Aires", "Madrid", "air123", List.of());
    }

    @Test
    void airline_shouldExistBeforeFlightsAreCreated() {
        assertDoesNotThrow(() -> {
            assertEquals("Test Airline", userService.getAirlineByNickname("air123",false).getName());
        });
    }

    @Test
    @DisplayName("GIVEN valid FlightDTO WHEN createFlight is called THEN flight is added")
    void createFlight_shouldAddFlightToDb() {
        // Crear airline y ciudades necesarias
        cityService.createCity(new CityDTO("CityA", "PaísA", 0.0, 0.0));
        cityService.createCity(new CityDTO("CityB", "PaísB", 1.0, 1.0));

        // Crear vuelo
        BaseFlightDTO base = new BaseFlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, null);
        flightService.createFlight(base, "air123", "AAA");

        // Verificar
        List<FlightDTO> allFlights = flightService.getAllFlights();
//        System.out.println("All Flights: " + allFlights);
//        System.out.println("Flight Names: " + allFlights.stream().map(FlightDTO::getName).collect(Collectors.toList()));
        assertEquals(1, allFlights.size());
        assertEquals("Vuelo 1", allFlights.get(0).getName());
    }
    @Test
    @DisplayName("GIVEN duplicate flight name WHEN createFlight is called THEN throw exception")
    void createFlight_shouldNotAllowDuplicates() {
        BaseFlightDTO base = new BaseFlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, null);
        flightService.createFlight(base, "air123", "AAA");

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightService.createFlight(base, "air123", "AAA");
        });

        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, base.getName()), ex.getMessage());
        assertEquals(1, flightService.getAllFlights().size());
    }

    @Test
    @DisplayName("GIVEN multiple flights WHEN getAllFlights is called THEN return all")
    void getAllFlights_shouldReturnAllFlights() {
        flightService.createFlight(new BaseFlightDTO("Vuelo A", LocalDateTime.now().plusDays(1), 90L, 100, 50, null), "air123", "AAA");
        flightService.createFlight(new BaseFlightDTO("Vuelo B", LocalDateTime.now().plusDays(2), 60L, 80, 40, null), "air123", "AAA");

        List<FlightDTO> flights = flightService.getAllFlights();

        assertEquals(2, flights.size());
        List<String> names = flights.stream().map(FlightDTO::getName).sorted().collect(Collectors.toList());
        assertEquals(List.of("Vuelo A", "Vuelo B"), names);
    }

    @Test
    @DisplayName("GIVEN existing flight WHEN getFlightDetailsByName is called THEN return flight DTO")
    void getFlightDetailsByName_shouldReturnCorrectFlight() {
        flightService.createFlight(
                new BaseFlightDTO("Vuelo Detalle", LocalDateTime.now().plusDays(1), 60L, 100, 50, null),
                "air123",
                "AAA"
        );

        FlightDTO result = flightService.getFlightDetailsByName("Vuelo Detalle");

        assertNotNull(result);
        assertEquals("Vuelo Detalle", result.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing flight WHEN getFlightDetailsByName is called THEN throw exception")
    void getFlightDetailsByName_shouldThrowIfNotFound() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlightDetailsByName("Vuelo Fantasma");
        });
        assertTrue(ex.getMessage().contains("Vuelo Fantasma"));
    }

    @Test
    @DisplayName("GIVEN existing flight WHEN getFlightByName is called THEN return flight")
    void getFlightByName_shouldReturnFlight() {
        flightService.createFlight(
                new BaseFlightDTO("Vuelo Buscado", LocalDateTime.now().plusDays(1), 90L, 80, 40, null),
                "air123",
                "AAA"
        );

        Flight flight = flightService.getFlightByName("Vuelo Buscado");

        assertNotNull(flight);
        assertEquals("Vuelo Buscado", flight.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing flight WHEN getFlightByName is called THEN throw exception")
    void getFlightByName_shouldThrowIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlightByName("Vuelo Inexistente");
        });
        assertTrue(ex.getMessage().contains("Vuelo Inexistente"));
    }

    @Test
    @DisplayName("GIVEN flights for an airline WHEN getAllFlightsByAirline is called THEN return flights")
    void getAllFlightsByAirline_shouldReturnFlightsForAirline() {
        flightService.createFlight(
                new BaseFlightDTO("Vuelo X", LocalDateTime.now().plusDays(1), 60L, 100, 50, null),
                "air123",
                "AAA"
        );

        List<FlightDTO> result = flightService.getAllFlightsByAirline("air123");

        assertEquals(1, result.size());
        assertEquals("Vuelo X", result.get(0).getName());
    }

    @Test
    @DisplayName("GIVEN non-existing airline WHEN getAllFlightsByAirline is called THEN throw exception")
    void getAllFlightsByAirline_shouldThrowIfAirlineNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getAllFlightsByAirline("noExiste");
        });
        assertEquals(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, "noExiste"), ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN route AAA WHEN creating flight with that route THEN flight is assigned")
    void createFlight_shouldAssignFlightRouteIfExists() {
        flightService.createFlight(
                new BaseFlightDTO("Vuelo Con Ruta", LocalDateTime.now().plusDays(1), 120L, 100, 50, null),
                "air123",
                "AAA"
        );

        Flight flight = flightService.getFlightByName("Vuelo Con Ruta");
        assertNotNull(flight.getFlightRoute());
        assertEquals("AAA", flight.getFlightRoute().getName());
    }
}
