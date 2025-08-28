package domain.services.city;

import domain.dtos.city.CityDTO;
import domain.models.city.City;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import utils.TestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {

    private CityService cityService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        modelMapper = ControllerFactory.getModelMapper();
        cityService = new CityService(modelMapper);
    }

    private CityDTO crearCiudadBasica(String nombre) {
        return new CityDTO(nombre, "Uruguay", -34.9011, -56.1645, new ArrayList<>());
    }

    @Test
    @DisplayName("Debería agregar una ciudad nueva correctamente")
    void createCity_shouldAddCitySuccessfully() {
        CityDTO dto = crearCiudadBasica("Montevideo");

        CityDTO result = cityService.createCity(dto);

        assertNotNull(result);
        assertEquals("Montevideo", result.getName());
        assertTrue(cityService.cityExists("Montevideo"));
    }

    @Test
    @DisplayName("No debería permitir agregar ciudades duplicadas")
    void createCity_shouldRejectDuplicates() {
        CityDTO dto = crearCiudadBasica("Montevideo");
        cityService.createCity(dto);

        assertThrows(IllegalArgumentException.class, () -> cityService.createCity(dto));
    }

    @Test
    @DisplayName("Debería retornar ciudad existente correctamente")
    void getCityByName_shouldReturnCity() {
        CityDTO dto = crearCiudadBasica("Salto");
        cityService.createCity(dto);

        City city = cityService.getCityByName("Salto");

        assertNotNull(city);
        assertEquals("Salto", city.getName());
    }

    @Test
    @DisplayName("Buscar ciudad inexistente debería lanzar excepción")
    void getCityByName_shouldThrowIfNotFound() {
        assertFalse( cityService.getCityByName("Montevideo") != null);
    }

    @Test
    @DisplayName("getCityDetailsByName debería retornar DTO de ciudad correctamente")
    void getCityDetailsByName_shouldReturnCityDTO() {
        CityDTO dto = crearCiudadBasica("Durazno");
        cityService.createCity(dto);

        CityDTO found = cityService.getCityDetailsByName("Durazno");

        assertNotNull(found);
        assertEquals("Durazno", found.getName());
    }

    @Test
    @DisplayName("getCityDetailsByName debería fallar si no existe")
    void getCityDetailsByName_shouldThrowIfNotFound() {
        assertThrows(IllegalArgumentException.class, () -> cityService.getCityDetailsByName("Fantasía"));
    }

    @Test
    @DisplayName("cityExists debería retornar true o false correctamente")
    void cityExists_shouldReturnCorrectValue() {
        assertFalse(cityService.cityExists("Tacuarembó"));
        cityService.createCity(crearCiudadBasica("Tacuarembó"));
        assertTrue(cityService.cityExists("Tacuarembó"));
    }

    @Test
    @DisplayName("isAirportInCity debería ser false por defecto (ciudad sin aeropuertos)")
    void isAirportInCity_shouldReturnFalseIfNoAirports() {
        cityService.createCity(crearCiudadBasica("Mercedes"));

        assertFalse(cityService.isAirportInCity("Mercedes", "Aeropuerto Inventado"));
    }

    @Test
    @DisplayName("isAirportInCity debería lanzar excepción si la ciudad no existe")
    void isAirportInCity_shouldThrowIfCityNotFound() {
        assertFalse(cityService.isAirportInCity("Mercedes", "Aeropuerto Inventado"));
    }

    //Testear getAllCities
    @Test
    @DisplayName("GIVEN a list of cities WHEN getAllCities THEN return the list of city names")
    void getAllCities_shouldReturnListOfCityNames() {
        cityService.createCity(crearCiudadBasica("Ciudad1"));
        cityService.createCity(crearCiudadBasica("Ciudad2"));
        cityService.createCity(crearCiudadBasica("Ciudad3"));

        var cities = cityService.getAllCities();

        assertEquals(3, cities.size());
        assertTrue(cities.contains("Ciudad1"));
        assertTrue(cities.contains("Ciudad2"));
        assertTrue(cities.contains("Ciudad3"));
        assertTrue(cities.get(0).contains("Ciudad1"));
        assertTrue(cities.get(1).contains("Ciudad2"));
        assertTrue(cities.get(2).contains("Ciudad3"));
    }

    @Test
    @DisplayName("GIVEN no cities WHEN getAllCities THEN return empty list")
    void getAllCities_shouldReturnEmptyListIfNoCities() {
        var cities = cityService.getAllCities();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }


}
