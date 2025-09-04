package domain.services.flight;

import static org.junit.jupiter.api.Assertions.*;

import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.models.flight.Flight;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ServiceFactory;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class FlightServiceTest {

    private IFlightService flightService;
    private IUserService userService;
    private IFlightRouteService flightRouteService;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        ModelMapper modelMapper = new ModelMapper();
        userService = new UserService(); // Ajustalo si usás un mapper real

        flightService = ServiceFactory.getFlightService();
        flightRouteService = ServiceFactory.getFlightRouteService();

        BaseAirlineDTO baseAirlineDTO = new BaseAirlineDTO(
                "air123",
                "Test Airline",
                "test@mail.com",
                "Una aerolínea de pruebaaaaaaa",
                "www.testair.com"
        );
        userService.registerAirline(baseAirlineDTO);

        // Crear ruta de vuelo 'A' que algunos tests usan
        BaseFlightRouteDTO baseRoute = new BaseFlightRouteDTO();
        baseRoute.setName("AAA");
        baseRoute.setDescription("Ruta de pruebaaaaaaaaaa");
        baseRoute.setCreatedAt(LocalDate.now());
        baseRoute.setPriceTouristClass(1000.0);
        baseRoute.setPriceBusinessClass(2000.0);
        baseRoute.setPriceExtraUnitBaggage(300.0);

        // Crear ciudades necesarias (puede moverse a helper si lo usás en otros tests)
        ServiceFactory.getCityService().createCity( new BaseCityDTO("Buenos Aires", "Argentina", -34.6037, -58.3816));
        ServiceFactory.getCityService().createCity( new BaseCityDTO("Madrid", "España", 40.4168, -3.7038));

        flightRouteService.createFlightRoute(
                baseRoute,
                "Buenos Aires",
                "Madrid",
                "air123",
                null
        );
    }

    @Test
    void airline_shouldExistBeforeFlightsAreCreated() {
        assertDoesNotThrow(() ->
                assertEquals("Test Airline", userService.getAirlineByNickname("air123", false).getName())
        );
    }

    @Test
    @DisplayName("GIVEN valid FlightDTO WHEN createFlight is called THEN flight is added")
    void createFlight_shouldAddFlightToDb() {
        BaseFlightDTO dto = new BaseFlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, LocalDateTime.now());
        flightService.createFlight(dto, "air123", "AAA");

        List<FlightDTO> allFlights = flightService.getAllFlights(false);
        assertEquals(1, allFlights.size());
        assertEquals("Vuelo 1", allFlights.get(0).getName());
    }

    @Test
    @DisplayName("GIVEN duplicate flight name WHEN createFlight is called THEN throw exception")
    void createFlight_shouldNotAllowDuplicates() {
        BaseFlightDTO dto = new BaseFlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, LocalDateTime.now());
        flightService.createFlight(dto, "air123", "AAA");

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightService.createFlight(dto, "air123", "AAA");
        });
        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, dto.getName()), ex.getMessage());
        assertEquals(1, flightService.getAllFlights(false).size());
    }

    @Test
    @DisplayName("GIVEN multiple flights WHEN getAllFlights is called THEN return all")
    void getAllFlights_shouldReturnAllFlights() {
        flightService.createFlight(new BaseFlightDTO("Vuelo A", LocalDateTime.now().plusDays(1), 90L, 100, 50, LocalDateTime.now()), "air123", "AAA");
        flightService.createFlight(new BaseFlightDTO("Vuelo B", LocalDateTime.now().plusDays(2), 60L, 80, 40, LocalDateTime.now()), "air123", "AAA");

        List<FlightDTO> flights = flightService.getAllFlights(false);

        assertEquals(2, flights.size());
        List<String> names = flights.stream().map(FlightDTO::getName).sorted().collect(Collectors.toList());
        assertEquals(List.of("Vuelo A", "Vuelo B"), names);
    }

    @Test
    @DisplayName("GIVEN existing flight WHEN getFlightDetailsByName is called THEN return flight DTO")
    void getFlightDetailsByName_shouldReturnCorrectFlight() {
        flightService.createFlight(new BaseFlightDTO("Vuelo Detalle", LocalDateTime.now().plusDays(1), 60L, 100, 50, LocalDateTime.now()), "air123", "AAA");

        FlightDTO result = flightService.getFlightDetailsByName("Vuelo Detalle", false);

        assertNotNull(result);
        assertEquals("Vuelo Detalle", result.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing flight WHEN getFlightDetailsByName is called THEN throw exception")
    void getFlightDetailsByName_shouldThrowIfNotFound() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlightDetailsByName("Vuelo Fantasma", false);
        });
        assertTrue(ex.getMessage().contains("Vuelo Fantasma"));
    }

    @Test
    @DisplayName("GIVEN existing flight WHEN getFlightByName is called THEN return flight")
    void getFlightByName_shouldReturnFlight() {
        flightService.createFlight(new BaseFlightDTO("Vuelo Buscado", LocalDateTime.now().plusDays(1), 90L, 80, 40, LocalDateTime.now()), "air123", "AAA");

        Flight flight = flightService.getFlightByName("Vuelo Buscado", false);

        assertNotNull(flight);
        assertEquals("Vuelo Buscado", flight.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing flight WHEN getFlightByName is called THEN throw exception")
    void getFlightByName_shouldThrowIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlightByName("Vuelo Inexistente", false);
        });
        assertTrue(ex.getMessage().contains("Vuelo Inexistente"));
    }

    @Test
    @DisplayName("GIVEN flights for an airline WHEN getAllFlightsByAirline is called THEN return flights")
    void getAllFlightsByAirline_shouldReturnFlightsForAirline() {
        flightService.createFlight(new BaseFlightDTO("Vuelo X", LocalDateTime.now().plusDays(1), 60L, 100, 50, LocalDateTime.now()), "air123", "AAA");

        List<FlightDTO> result = flightService.getAllFlightsByAirlineNickname("air123", false);

        assertEquals(1, result.size());
        assertEquals("Vuelo X", result.get(0).getName());
    }

    @Test
    @DisplayName("GIVEN non-existing airline WHEN getAllFlightsByAirline is called THEN throw exception")
    void getAllFlightsByAirline_shouldThrowIfAirlineNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getAllFlightsByAirlineNickname("noExiste", false);
        });
        assertEquals(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, "noExiste"), ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN route AAA WHEN creating flight with that route THEN flight is assigned")
    void createFlight_shouldAssignFlightRouteIfExists() {
        BaseFlightDTO dto = new BaseFlightDTO("Vuelo Con Ruta", LocalDateTime.now().plusDays(1), 120L, 100, 50, LocalDateTime.now());

        flightService.createFlight(dto, "air123", "AAA");

        Flight flight = flightService.getFlightByName("Vuelo Con Ruta", true); // True porque utilizamos la flight route abajo
        assertNotNull(flight.getFlightRoute());
        assertEquals("AAA", flight.getFlightRoute().getName());
    }

}
