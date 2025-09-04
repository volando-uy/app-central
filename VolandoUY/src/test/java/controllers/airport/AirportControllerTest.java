package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.services.airport.IAirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AirportControllerTest {

    private IAirportService airportService;
    private IAirportController airportController;

    @BeforeEach
    void setUp() {
        airportService = mock(IAirportService.class);
        airportController = new AirportController(airportService);
    }

    @Test
    @DisplayName("GIVEN valid AirportDTO and city WHEN createAirport is called THEN it should return created airport")
    void createAirport_shouldReturnCreatedAirport() {
        // GIVEN
        AirportDTO input = new AirportDTO("Carrasco", "MVD", "Montevideo");
        when(airportService.createAirport(input, "Montevideo")).thenReturn(input);

        // WHEN
        AirportDTO result = airportController.createAirport(input, "Montevideo");

        // THEN
        assertNotNull(result);
        assertEquals("Carrasco", result.getName());
        assertEquals("MVD", result.getCode());
        verify(airportService).createAirport(input, "Montevideo");
    }

    @Test
    @DisplayName("GIVEN existing code WHEN getAirportByCode is called THEN correct airport is returned")
    void getAirportByCode_shouldReturnCorrectDTO() {
        // GIVEN
        BaseAirportDTO expected = new BaseAirportDTO("Carrasco", "MVD");
        when(airportService.getAirportDetailsByCode("MVD", false)).thenReturn(expected);

        // WHEN
        AirportDTO result = airportController.getAirportDetailsByCode("MVD");

        // THEN
        assertNotNull(result);
        assertEquals("MVD", result.getCode());
        verify(airportService).getAirportDetailsByCode("MVD", false);
    }

    @Test
    @DisplayName("GIVEN airport exists WHEN airportExists is called THEN return true")
    void airportExists_shouldReturnTrueIfExists() {
        // GIVEN
        when(airportService.airportExists("MVD")).thenReturn(true);

        // WHEN
        boolean exists = airportController.airportExists("MVD");

        // THEN
        assertTrue(exists);
        verify(airportService).airportExists("MVD");
    }

    @Test
    @DisplayName("GIVEN airport does not exist WHEN airportExists is called THEN return false")
    void airportExists_shouldReturnFalseIfNotExists() {
        // GIVEN
        when(airportService.airportExists("XXX")).thenReturn(false);

        // WHEN
        boolean exists = airportController.airportExists("XXX");

        // THEN
        assertFalse(exists);
        verify(airportService).airportExists("XXX");
    }
}
