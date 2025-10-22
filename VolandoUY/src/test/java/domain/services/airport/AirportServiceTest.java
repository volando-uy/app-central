package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.services.city.CityService;
import domain.services.city.ICityService;
import factory.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AirportServiceTest {

    private IAirportService airportService;
    private ICityService cityService;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        cityService = ServiceFactory.getCityService();
        airportService = ServiceFactory.getAirportService();

        cityService.createCity(new BaseCityDTO("Montevideo", "Uruguay", -34.9, -56.2));
    }

    @Test
    @DisplayName("Crear aeropuerto válido debería agregarlo correctamente")
    void createAirport_shouldAddCorrectly() {
        BaseAirportDTO dto = new BaseAirportDTO("Carrasco", "MVD");

        BaseAirportDTO result = airportService.createAirport(dto, "Montevideo");

        assertNotNull(result);
        assertEquals("MVD", result.getCode());
        assertEquals("Carrasco", result.getName());
    }

    @Test
    @DisplayName("No debería permitir duplicados por código")
    void createAirport_shouldFailOnDuplicateCode() {
        BaseAirportDTO dto = new BaseAirportDTO("Carrasco", "MVD");

        airportService.createAirport(dto, "Montevideo");

        assertThrows(IllegalArgumentException.class, () -> {
            airportService.createAirport(dto, "Montevideo");
        });
    }

    @Test
    @DisplayName("getAirportByCode debería devolver el aeropuerto correcto")
    void getAirportByCode_shouldReturnCorrectAirport() {
        BaseAirportDTO dto = new BaseAirportDTO("Carrasco", "MVD");
        airportService.createAirport(dto, "Montevideo");

        assertTrue(airportService.airportExists("MVD"));

        var airport = airportService.getAirportByCode("MVD", false);

        assertEquals("Carrasco", airport.getName());
        assertEquals("MVD", airport.getCode());
    }

    @Test
    @DisplayName("getAirportDetailsByCode debería retornar DTO correctamente")
    void getAirportDetailsByCode_shouldReturnDTO() {
        BaseAirportDTO dto = new BaseAirportDTO("Carrasco", "MVD");
        airportService.createAirport(dto, "Montevideo");

        AirportDTO result = airportService.getAirportDetailsByCode("MVD", false);

        assertEquals("Carrasco", result.getName());
        assertEquals("MVD", result.getCode());
        assertEquals("Montevideo", result.getCityName());
    }

    @Test
    @DisplayName("Buscar aeropuerto inexistente debería lanzar excepción")
    void getAirportByCode_shouldThrowIfNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            airportService.getAirportByCode("ZZZ", false);
        });
    }

    @Test
    @DisplayName("airportExists debería detectar correctamente existencia")
    void airportExists_shouldReturnTrueOrFalse() {
        assertFalse(airportService.airportExists("MVD"));

        BaseAirportDTO dto = new BaseAirportDTO("Carrasco", "MVD");
        airportService.createAirport(dto, "Montevideo");

        assertTrue(airportService.airportExists("MVD"));
    }

    @Test
    @DisplayName("getAllAirportsDetails debería retornar lista correcta")
    void getAllAirportsDetails_shouldReturnCorrectList() {
        BaseAirportDTO dto1 = new BaseAirportDTO("Carrasco", "MVD");
        BaseAirportDTO dto2 = new BaseAirportDTO("Laguna del Sauce", "MVN");

        airportService.createAirport(dto1, "Montevideo");
        airportService.createAirport(dto2, "Montevideo");

        // Without full
        var airports = airportService.getAllAirportsDetails(false);

        assertEquals(2, airports.size());
        ArrayList<String> codes = new ArrayList<>();
        for (var a : airports) {
            codes.add(a.getCode());
        }
        assertTrue(codes.contains("MVD"));
        assertTrue(codes.contains("MVN"));

        // With full
        airports = airportService.getAllAirportsDetails(true);

        assertEquals(2, airports.size());
        codes = new ArrayList<>();
        for (var a : airports) {
            codes.add(a.getCode());
        }
        assertTrue(codes.contains("MVD"));
        assertTrue(codes.contains("MVN"));

    }


}
