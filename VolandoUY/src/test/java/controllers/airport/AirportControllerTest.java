package controllers.airport;

import controllers.city.ICityController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.CityDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirportControllerTest {

    private IAirportController airportController;
    private ICityController cityController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        airportController = ControllerFactory.getAirportController();
        cityController = ControllerFactory.getCityController();

        // Crear ciudad base para los tests
        CityDTO city = new CityDTO();
        city.setName("Montevideo");
        city.setCountry("Uruguay");
        city.setLongitude(-34.9);
        city.setLongitude(-56.2);
        city.setAirportNames(List.of(city.getName()));
        cityController.createCity(city);
    }

    @Test
    @DisplayName("GIVEN valid AirportDTO and city WHEN createAirport is called THEN it should return created airport")
    void createAirport_shouldReturnCreatedAirport() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");
        dto.setCityName("Montevideo");

        BaseAirportDTO created = airportController.createAirport(dto, "Montevideo");

        assertNotNull(created);
        assertEquals("MVD", created.getCode());
        assertEquals("Carrasco", created.getName());
    }

    @Test
    @DisplayName("GIVEN existing code WHEN getAirportByCode is called THEN correct airport is returned")
    void getAirportByCode_shouldReturnCorrectDTO() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");
        dto.setCityName("Montevideo");


        airportController.createAirport(dto, "Montevideo");

        AirportDTO result = airportController.getAirportDetailsByCode("MVD");

        assertNotNull(result);
        assertEquals("MVD", result.getCode());
        assertEquals("Carrasco", result.getName());
        assertEquals("Montevideo", result.getCityName());
    }

    @Test
    @DisplayName("GIVEN airport exists WHEN airportExists is called THEN return true")
    void airportExists_shouldReturnTrueIfExists() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");
        dto.setCityName("Montevideo");

        airportController.createAirport(dto, "Montevideo");

        assertTrue(airportController.airportExists("MVD"));
    }

    @Test
    @DisplayName("GIVEN airport does not exist WHEN airportExists is called THEN return false")
    void airportExists_shouldReturnFalseIfNotExists() {
        assertFalse(airportController.airportExists("XYZ"));
    }

    @Test
    @DisplayName("GIVEN duplicate code WHEN createAirport is called THEN throw exception")
    void createAirport_shouldFailOnDuplicateCode() {
        AirportDTO dto = new AirportDTO();
        dto.setName("Carrasco");
        dto.setCode("MVD");
        dto.setCityName("Montevideo");

        airportController.createAirport(dto, "Montevideo");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            airportController.createAirport(dto, "Montevideo");
        });
        System.out.println(ex.getMessage());
        assertTrue(String.format(ErrorMessages.ERR_AIRPORT_CODE_ALREADY_EXISTS, "MVD").contains("MVD"));
    }
}
