package controllers.flightRoute;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.services.flightRoute.IFlightRouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRouteControllerTest {

    private IFlightRouteService flightRouteService;
    private IFlightRouteController flightRouteController;

    @BeforeEach
    void setUp() {
        flightRouteService = mock(IFlightRouteService.class);
        flightRouteController = new FlightRouteController(flightRouteService);
    }

    @Test
    @DisplayName("Debe verificar existencia de una ruta de vuelo")
    void existFlightRoute_shouldCallServiceAndReturnCorrectly() {
        // GIVEN
        when(flightRouteService.existFlightRoute("Ruta 1")).thenReturn(true);

        // WHEN
        boolean result = flightRouteController.existFlightRoute("Ruta 1");

        // THEN
        assertTrue(result);
        verify(flightRouteService).existFlightRoute("Ruta 1");
    }

    @Test
    @DisplayName("Debe delegar la creación de una ruta de vuelo")
    void createFlightRoute_shouldCallServiceAndReturnDTO() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO(
                "Ruta 1", "Descripción", LocalDate.now(),
                100.0, 150.0, 30.0,
                "Montevideo", "Buenos Aires",
                "air123",
                List.of("Económica", "Business"),
                List.of("Vuelo1", "Vuelo2")
        );

        when(flightRouteService.createFlightRoute(dto)).thenReturn(dto);

        // WHEN
        FlightRouteDTO result = flightRouteController.createFlightRoute(dto);

        // THEN
        assertNotNull(result);
        assertEquals("Ruta 1", result.getName());
        verify(flightRouteService).createFlightRoute(dto);
    }

    @Test
    @DisplayName("Debe retornar detalles de una ruta de vuelo por nombre")
    void getFlightRouteByName_shouldReturnFromService() {
        // GIVEN
        FlightRouteDTO dto = new FlightRouteDTO(
                "Ruta 1", "Desc", LocalDate.now(),
                90.0, 140.0, 25.0,
                "Montevideo", "Santiago",
                "flyuy",
                List.of("Económica"),
                List.of("VueloA")
        );
        when(flightRouteService.getFlightRouteDetailsByName("Ruta 1")).thenReturn(dto);

        // WHEN
        FlightRouteDTO result = flightRouteController.getFlightRouteByName("Ruta 1");

        // THEN
        assertNotNull(result);
        assertEquals("Ruta 1", result.getName());
        assertEquals("Montevideo", result.getOriginCityName());
        verify(flightRouteService).getFlightRouteDetailsByName("Ruta 1");
    }

    @Test
    @DisplayName("Debe retornar todas las rutas por aerolínea")
    void getAllFlightRoutesByAirlineNickname_shouldReturnListFromService() {
        // GIVEN
        List<FlightRouteDTO> mockRoutes = List.of(
                new FlightRouteDTO("Ruta A", "Desc A", LocalDate.now(), 90.0, 130.0, 20.0, "MVD", "BUE", "air123", List.of(), List.of()),
                new FlightRouteDTO("Ruta B", "Desc B", LocalDate.now(), 110.0, 160.0, 25.0, "MVD", "RIO", "air123", List.of(), List.of())
        );

        when(flightRouteService.getAllFlightRoutesDetailsByAirlineNickname("air123")).thenReturn(mockRoutes);

        // WHEN
        List<FlightRouteDTO> result = flightRouteController.getAllFlightRoutesByAirlineNickname("air123");

        // THEN
        assertEquals(2, result.size());
        assertEquals("Ruta A", result.get(0).getName());
        assertEquals("Ruta B", result.get(1).getName());
        verify(flightRouteService).getAllFlightRoutesDetailsByAirlineNickname("air123");
    }
}
