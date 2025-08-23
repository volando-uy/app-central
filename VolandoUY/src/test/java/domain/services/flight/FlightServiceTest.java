package domain.services.flight;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import domain.dtos.flight.FlightDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

class FlightServiceTest {

    private IFlightService flightService;
    private IUserService userService;
    private MockedStatic<ControllerFactory> factoryMock;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        userService = new UserService(modelMapper, new UserMapper(modelMapper));

        // Mockeamos ControllerFactory para que devuelva nuestro userService
        factoryMock = mockStatic(ControllerFactory.class);
        factoryMock.when(ServiceFactory::getUserService).thenReturn(userService);

        flightService = new FlightService(modelMapper);

        AirlineDTO airlineDTO = new AirlineDTO(
                "air123",
                "Test Airline",
                "test@mail.com",
                "Una aerolínea de prueba",
                "www.testair.com"
        );
        userService.registerAirline(airlineDTO);
    }

    @AfterEach
    void tearDown() {
        factoryMock.close(); // ¡Nunca te olvides de cerrar el mock!
    }

    @Test
    void airline_shouldExistBeforeFlightsAreCreated() {
        // GIVEN una aerolínea previamente registrada en el sistema
        // WHEN la buscamos por su nickname
        Airline a = userService.getAirlineByNickname("air123");

        // THEN se obtiene correctamente y sus datos son válidos
        assertDoesNotThrow(() -> assertEquals("Test Airline", a.getName()));
    }

    @Test
    @DisplayName("GIVEN valid FlightDTO WHEN createFlight is called THEN flight is added")
    void createFlight_shouldAddFlightToDb() {
        // GIVEN un FlightDTO válido y una aerolínea registrada
        FlightDTO dto = new FlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, null, "air123");

        // WHEN se crea el vuelo
        flightService.createFlight(dto);

        // THEN el vuelo se guarda y se puede recuperar
        List<FlightDTO> allFlights = flightService.getAllFlights();
        assertEquals(1, allFlights.size());
        assertEquals("Vuelo 1", allFlights.get(0).getName());
    }

    @Test
    @DisplayName("GIVEN duplicate flight name WHEN createFlight is called THEN throw exception")
    void createFlight_shouldNotAllowDuplicates() {
        // GIVEN un vuelo ya creado con nombre "Vuelo 1"
        FlightDTO dto = new FlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, null, "air123");
        flightService.createFlight(dto);

        // WHEN se intenta crear el mismo vuelo nuevamente
        // THEN se lanza una excepción indicando duplicado
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightService.createFlight(dto);
        });

        assertEquals("Flight already exists: Vuelo 1", ex.getMessage());
        assertEquals(1, flightService.getAllFlights().size());
    }

    @Test
    @DisplayName("GIVEN multiple flights WHEN getAllFlights is called THEN return all")
    void getAllFlights_shouldReturnAllFlights() {
        // GIVEN dos vuelos distintos agregados al sistema
        flightService.createFlight(new FlightDTO("Vuelo A", LocalDateTime.now().plusDays(1), 90L, 100, 50, null, "air123"));
        flightService.createFlight(new FlightDTO("Vuelo B", LocalDateTime.now().plusDays(2), 60L, 80, 40, null, "air123"));

        // WHEN se solicitan todos los vuelos
        List<FlightDTO> flights = flightService.getAllFlights();

        // THEN se devuelven correctamente ambos vuelos con sus nombres
        assertEquals(2, flights.size());
        assertEquals("Vuelo A", flights.get(0).getName());
        assertEquals("Vuelo B", flights.get(1).getName());
    }

    @Test
    @DisplayName("GIVEN existing flight WHEN getFlightDetailsByName is called THEN return flight DTO")
    void getFlightDetailsByName_shouldReturnCorrectFlight() {
        // GIVEN un vuelo creado con un nombre específico
        flightService.createFlight(new FlightDTO("Vuelo Detalle", LocalDateTime.now().plusDays(1), 60L, 100, 50, null, "air123"));

        // WHEN se solicita el detalle del vuelo por su nombre
        FlightDTO result = flightService.getFlightDetailsByName("Vuelo Detalle");

        // THEN se obtiene el DTO correcto con los datos esperados
        assertNotNull(result);
        assertEquals("Vuelo Detalle", result.getName());
    }


    @Test
    @DisplayName("GIVEN non-existing flight WHEN getFlightDetailsByName is called THEN return null")
    void getFlightDetailsByName_shouldReturnNullIfNotFound() {
        // GIVEN no vuelos creados con el nombre "Vuelo Fantasma"
        FlightDTO result = flightService.getFlightDetailsByName("Vuelo Fantasma");
        // WHEN se solicita el detalle de un vuelo inexistente
        // THEN se obtiene null ya que no existe
        assertNull(result);
    }

    @Test
    @DisplayName("GIVEN existing flight WHEN getFlightByName is called THEN return flight")
    void getFlightByName_shouldReturnFlight() {
        // GIVEN un vuelo creado con un nombre específico
        flightService.createFlight(new FlightDTO("Vuelo Buscado", LocalDateTime.now().plusDays(1), 90L, 80, 40, null, "air123"));

        // WHEN se busca el vuelo por su nombre
        Flight flight = flightService.getFlightByName("Vuelo Buscado");

        // THEN se obtiene el vuelo correcto con los datos esperados
        assertNotNull(flight);
        assertEquals("Vuelo Buscado", flight.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing flight WHEN getFlightByName is called THEN throw exception")
    void getFlightByName_shouldThrowIfNotFound() {
        // GIVEN no vuelos creados con el nombre "Vuelo Inexistente"
        // WHEN se intenta buscar un vuelo inexistente
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getFlightByName("Vuelo Inexistente");
        });
        // THEN se lanza una excepción indicando que no se encontró el vuelo
        assertTrue(ex.getMessage().contains("Vuelo Inexistente"));
    }


    @Test
    @DisplayName("GIVEN flights for an airline WHEN getAllFlightsByAirline is called THEN return flights")
    void getAllFlightsByAirline_shouldReturnFlightsForAirline() {
        // GIVEN una aerolínea con varios vuelos
        flightService.createFlight(new FlightDTO("Vuelo X", LocalDateTime.now().plusDays(1), 60L, 100, 50, null, "air123"));
        // GIVEN un vuelo de otra aerolínea
        // WHEN se solicitan los vuelos de esa aerolínea
        List<FlightDTO> result = flightService.getAllFlightsByAirline("air123");
        // THEN se devuelven correctamente los vuelos asociados a esa aerolínea
        assertEquals(1, result.size());
        assertEquals("Vuelo X", result.get(0).getName());
    }
    @Test
    @DisplayName("GIVEN non-existing airline WHEN getAllFlightsByAirline is called THEN throw exception")
    void getAllFlightsByAirline_shouldThrowIfAirlineNotFound() {
        // GIVEN una aerolínea que no existe en el sistema
        // WHEN se intenta obtener los vuelos de una aerolínea inexistente
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightService.getAllFlightsByAirline("noExiste");
        });
        // THEN se lanza una excepción indicando que no se encontró la aerolínea
        assertEquals("Airline no encontrada: noExiste", ex.getMessage());
    }

}
