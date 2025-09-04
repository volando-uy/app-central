package controllers.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.services.flight.IFlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightControllerTest {

    private IFlightService flightService;
    private IFlightController flightController;

    @BeforeEach
    void setUp() {
        flightService = mock(IFlightService.class);
        flightController = new FlightController(flightService);
    }

    @Test
    @DisplayName("GIVEN valid FlightDTO WHEN createFlight is called THEN it should return created FlightDTO")
    void createFlight_shouldDelegateToServiceAndReturnDTO() {
        // GIVEN
        FlightDTO flightDTO = new FlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, null, "air123","A");

        when(flightService.createFlight(flightDTO)).thenReturn(flightDTO);

        // WHEN
        FlightDTO result = flightController.createFlight(flightDTO);

        // THEN
        assertNotNull(result);
        assertEquals("Vuelo 1", result.getName());
        verify(flightService).createFlight(flightDTO);
    }

    @Test
    @DisplayName("GIVEN multiple flights WHEN getAllFlights is called THEN return full list")
    void getAllFlights_shouldReturnListFromService() {
        // GIVEN
        List<FlightDTO> mockFlights = List.of(
                new FlightDTO("Vuelo A", LocalDateTime.now(), 90L, 80, 40, LocalDateTime.now()),
                new FlightDTO("Vuelo B", LocalDateTime.now(), 60L, 70, 35, LocalDateTime.now())
        );
        when(flightService.getAllFlights(false)).thenReturn(mockFlights);

        // WHEN
        List<BaseFlightDTO> result = flightController.getAllFlightsSimpleDetails();

        // THEN
        assertEquals(2, result.size());
        assertEquals("Vuelo A", result.get(0).getName());
        verify(flightService).getAllFlights(false);
    }

    @Test
    @DisplayName("GIVEN flight name WHEN getFlightByName is called THEN return correct DTO")
    void getFlightByName_shouldReturnFlightDTO() {
        // GIVEN
        FlightDTO dto = new FlightDTO("Vuelo 1", LocalDateTime.now(), 120L, 100, 50, null, "air123","A");

        when(flightService.getFlightDetailsByName("Vuelo 1")).thenReturn(dto);

        // WHEN
        FlightDTO result = flightController.getFlightByName("Vuelo 1");

        // THEN
        assertNotNull(result);
        assertEquals("Vuelo 1", result.getName());
        verify(flightService).getFlightDetailsByName("Vuelo 1");
    }

    @Test
    @DisplayName("GIVEN airline nickname WHEN getAllFlightsByAirline is called THEN return list from service")
    void getAllFlightsByAirline_shouldReturnFlightsForAirline() {
        // GIVEN
        List<FlightDTO> flights = List.of(
                new FlightDTO("Vuelo X", LocalDateTime.now(), 90L, 80, 40, null, "flyuy","A"),
                new FlightDTO("Vuelo Y", LocalDateTime.now(), 100L, 90, 45, null, "flyuy","A")
        );
        when(flightService.getAllFlightsByAirline("flyuy")).thenReturn(flights);

        // WHEN
        List<FlightDTO> result = flightController.getAllFlightsByAirline("flyuy");

        // THEN
        assertEquals(2, result.size());
        assertEquals("Vuelo X", result.get(0).getName());
        verify(flightService).getAllFlightsByAirline("flyuy");
    }
}
