package controllers.city;

import controllers.airport.IAirportController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityControllerTest {

    private ICityController cityController;
    private IAirportController airportController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        cityController = ControllerFactory.getCityController();
        airportController = ControllerFactory.getAirportController();

        // ----------- Crear ciudad 1: Montevideo (sin aeropuertos) -----------
        BaseCityDTO montevideo = new BaseCityDTO();
        montevideo.setName("Montevideo");
        montevideo.setCountry("Uruguay");
        montevideo.setLatitude(-34.9);
        montevideo.setLongitude(-56.2);
        cityController.createCity(montevideo);

        // ----------- Crear ciudad 2: Buenos Aires (sin aeropuertos inicialmente) -----------
        CityDTO buenosAires = new CityDTO();
        buenosAires.setName("Buenos Aires");
        buenosAires.setCountry("Argentina");
        buenosAires.setLatitude(-34.6);
        buenosAires.setLongitude(-58.4);
        // NO setees airportNames aún
        cityController.createCity(buenosAires);

        // Ahora creamos los aeropuertos y los asociamos a la ciudad
        airportController.createAirport(new AirportDTO("Ezeiza", "EZE"), "Buenos Aires");
        airportController.createAirport(new AirportDTO("Aeroparque", "AEP"), "Buenos Aires");

        // ----------- Crear ciudad 3: Santiago (sin aeropuertos inicialmente) -----------
        CityDTO santiago = new CityDTO();
        santiago.setName("Santiago");
        santiago.setCountry("Chile");
        santiago.setLatitude(-33.45);
        santiago.setLongitude(-70.66);
        // NO setees airportNames aún
        cityController.createCity(santiago);

        // Ahora le agregamos el aeropuerto a Santiago
        airportController.createAirport(new AirportDTO("Arturo Merino Benitez", "SCL"), "Santiago");
    }


    @Test
    @DisplayName("GIVEN valid CityDTO WHEN createCity is called THEN it should return the created DTO")
    void createCity_shouldReturnDTO() {
        BaseCityDTO dto = new CityDTO("Lima", "Peru", -12.0464, -77.0428);

        BaseCityDTO created = cityController.createCity(dto);

        assertNotNull(created);
        assertEquals("Lima", created.getName());
        assertEquals("Peru", created.getCountry());
    }

    @Test
    @DisplayName("GIVEN existing city WHEN getCitySimpleDetailsByName is called THEN it should return BaseCityDTO")
    void getCitySimpleDetailsByName_shouldReturnCorrectDTO() {
        BaseCityDTO result = cityController.getCitySimpleDetailsByName("Montevideo");

        assertNotNull(result);
        assertEquals("Montevideo", result.getName());
    }

    @Test
    @DisplayName("GIVEN a city name WHEN cityExists is called THEN it should return correct value")
    void cityExists_shouldReturnCorrectBoolean() {
        assertTrue(cityController.cityExists("Buenos Aires"));
        assertFalse(cityController.cityExists("NoExiste"));
    }

    @Test
    @DisplayName("GIVEN city and airport WHEN isAirportInCity is called THEN it should return correct boolean")
    void isAirportInCity_shouldReturnCorrectly() {
        assertTrue(cityController.isAirportInCity("Buenos Aires", "Ezeiza"));
        assertFalse(cityController.isAirportInCity("Buenos Aires", "Aeropuerto Inventado"));
    }

    @Test
    @DisplayName("GIVEN existing city WHEN getCityDetailsByName is called THEN it should return CityDTO")
    void getCityDetailsByName_shouldReturnCityDTO() {
        CityDTO result = cityController.getCityDetailsByName("Santiago");

        assertNotNull(result);
        assertEquals("Santiago", result.getName());
        assertEquals("Chile", result.getCountry());
        assertTrue(result.getAirportNames().contains("Arturo Merino Benitez"));
    }

    @Test
    @DisplayName("GIVEN cities exist WHEN getAllCities is called THEN return list of names")
    void getAllCities_shouldReturnListOfNames() {
        List<String> cities = cityController.getAllCities();

        assertNotNull(cities);
        assertTrue(cities.contains("Montevideo"));
        assertTrue(cities.contains("Buenos Aires"));
        assertTrue(cities.contains("Santiago"));
    }
}
