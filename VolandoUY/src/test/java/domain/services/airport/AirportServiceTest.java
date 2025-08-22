package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.services.city.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AirportServiceTest {

    private AirportService airportService;
    private CityService cityService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        cityService = new CityService(modelMapper);
        airportService = new AirportService(modelMapper, cityService);

        // Ciudad base para todos los tests
        cityService.createCity(new CityDTO("Montevideo", "Uruguay", -34.9, -56.2,new ArrayList<>()));
    }

    @Test
    @DisplayName("Crear aeropuerto válido debería agregarlo correctamente")
    void createAirport_shouldAddCorrectly() {
        AirportDTO dto = new AirportDTO("Carrasco", "MVD", "Montevideo");

        AirportDTO result = airportService.createAirport(dto, "Montevideo");

        assertNotNull(result);
        assertEquals("MVD", result.getCode());
        assertEquals("Carrasco", result.getName());
    }

    @Test
    @DisplayName("No debería permitir duplicados por código")
    void createAirport_shouldFailOnDuplicateCode() {
        AirportDTO dto = new AirportDTO("Carrasco", "MVD", "Montevideo");

        airportService.createAirport(dto, "Montevideo");

        assertThrows(IllegalArgumentException.class, () -> {
            airportService.createAirport(dto, "Montevideo");
        });
    }

    @Test
    @DisplayName("getAirportByCode debería devolver el aeropuerto correcto")
    void getAirportByCode_shouldReturnCorrectAirport() {
        AirportDTO dto = new AirportDTO("Carrasco", "MVD", "Montevideo");
        airportService.createAirport(dto, "Montevideo");

        assertTrue(airportService.airportExists("MVD"));

        var airport = airportService.getAirportByCode("MVD");

        assertEquals("Carrasco", airport.getName());
        assertEquals("MVD", airport.getCode());
    }

    @Test
    @DisplayName("getAirportDetailsByCode debería retornar DTO correctamente")
    void getAirportDetailsByCode_shouldReturnDTO() {
        //Crear ciudad
//        CityDTO cityDTO= new CityDTO("Montevideo", "Uruguay", -34.9, -56.2,new ArrayList<>());
//        cityService.createCity(cityDTO);
        AirportDTO dto = new AirportDTO("Carrasco", "MVD", "Montevideo");
        airportService.createAirport(dto, "Montevideo");

        AirportDTO result = airportService.getAirportDetailsByCode("MVD");

        assertEquals("Carrasco", result.getName());
        assertEquals("MVD", result.getCode());
    }

    @Test
    @DisplayName("Buscar aeropuerto inexistente debería lanzar excepción")
    void getAirportByCode_shouldThrowIfNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            airportService.getAirportByCode("ZZZ");
        });
    }

    @Test
    @DisplayName("airportExists debería detectar correctamente existencia")
    void airportExists_shouldReturnTrueOrFalse() {
        assertFalse(airportService.airportExists("MVD"));

        AirportDTO dto = new AirportDTO("Carrasco", "MVD", "Montevideo");
        airportService.createAirport(dto, "Montevideo");

        assertTrue(airportService.airportExists("MVD"));
    }
}
