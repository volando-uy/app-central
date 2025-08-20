package controllers.flight;

import controllers.flightRoute.FlightRouteController;
import controllers.flightRoute.IFlightRouteController;
import domain.dtos.flight.FlightDTO;

import domain.services.flight.IFlightService;
import domain.services.flightRoute.IFlightRouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FlightControllerTest {

    @Mock
    private IFlightService flightService;

    @InjectMocks
    private IFlightController flightController;

    @BeforeEach
    void setUp() {
        flightService = mock(IFlightService.class);
        flightController = new FlightController(flightService);
    }

    @Test
    @DisplayName("Debe llamar a createFlight correctamente")
    void createFlight_shouldCallTheServiceCorrectly() {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setName("test");

        // Act
        flightController.createFlight(flightDTO);

        // Assert
        verify(flightService).createFlight(flightDTO);
    }

    @Test
    @DisplayName("Debe llamar a getAllFlights correctamente")
    void getAllFlights_shouldCallTheServiceCorrectly() {
        // Act
        flightController.getAllFlights();

        // Assert
        verify(flightService).getAllFlights();
    }
}
