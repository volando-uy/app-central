package services.flight;

import domain.dtos.flight.FlightDTO;
import domain.services.flight.FlightService;
import domain.services.flight.IFlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightServiceTest {

    private IFlightService flightService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        flightService = new FlightService(modelMapper);
    }

    @Test
    @DisplayName("Should create and store a flight from DTO")
    void createFlight_shouldAddFlightToDb() {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setName("Flight 1");

        flightService.createFlight(flightDTO);

        assertEquals(1, flightService.getAllFlights().size());
        FlightDTO created = flightService.getAllFlights().get(0);
        assertEquals("Flight 1", created.getName());
    }

    @Test
    @DisplayName("Should not allow creating duplicate flights")
    void createFlight_shouldNotAllowDuplicates() {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setName("Flight 1");

        flightService.createFlight(flightDTO);

        try {
            flightService.createFlight(flightDTO);
        } catch (UnsupportedOperationException e) {
            assertEquals("Flight already exists: Flight 1", e.getMessage());
        }

        assertEquals(1, flightService.getAllFlights().size());
    }

    @Test
    @DisplayName("Should retrieve all stored flights")
    void getAllFlights_shouldReturnAllFlights() {
        FlightDTO flight1 = new FlightDTO();
        flight1.setName("Flight 1");
        FlightDTO flight2 = new FlightDTO();
        flight2.setName("Flight 2");

        flightService.createFlight(flight1);
        flightService.createFlight(flight2);

        assertEquals(2, flightService.getAllFlights().size());
        assertEquals("Flight 1", flightService.getAllFlights().get(0).getName());
        assertEquals("Flight 2", flightService.getAllFlights().get(1).getName());
    }
}
