package domain.services.city;

import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.models.city.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {

    private CityService cityService;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        cityService = new CityService();
    }

    private CityDTO crearCiudadBasica(String nombre) {
        CityDTO dto = new CityDTO();
        dto.setName(nombre);
        dto.setCountry("Uruguay");
        dto.setLatitude(-34.9011);
        dto.setLongitude(-56.1645);
        dto.setAirportNames(new ArrayList<>());
        return dto;
    }

    @Test
    @DisplayName("Debería agregar una ciudad nueva correctamente")
    void createCity_shouldAddCitySuccessfully() {
        CityDTO dto = crearCiudadBasica("Montevideo");

        BaseCityDTO result = cityService.createCity(dto);

        assertNotNull(result);
        assertEquals("Montevideo", result.getName());
        assertTrue(cityService.cityExists("Montevideo"));
    }

    @Test
    @DisplayName("No debería permitir agregar ciudades duplicadas")
    void createCity_shouldRejectDuplicates() {
        CityDTO dto = crearCiudadBasica("Montevideo-dup");

        // La primera creación NO debe lanzar
        assertDoesNotThrow(() -> cityService.createCity(dto));

        // La segunda SÍ debe lanzar por duplicado
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> cityService.createCity(dto));
        assertEquals("La ciudad Montevideo-dup ya existe", ex.getMessage());
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
    @DisplayName("Buscar ciudad inexistente debería retornar null")
    void getCityByName_shouldReturnNullIfNotFound() {
        City city = cityService.getCityByName("NoExiste");
        assertNull(city);
    }

    @Test
    @DisplayName("getCityDetailsByName debería retornar DTO de ciudad correctamente")
    void getCityDetailsByName_shouldReturnCityDTO() {
        CityDTO dto = crearCiudadBasica("Durazno");
        cityService.createCity(dto);

        CityDTO found = cityService.getCityDetailsByName("Durazno",false);

        assertNotNull(found);
        assertEquals("Durazno", found.getName());
    }

    @Test
    @DisplayName("getCityDetailsByName debería fallar si no existe")
    void getCityDetailsByName_shouldThrowIfNotFound() {
        assertThrows(IllegalArgumentException.class, () -> cityService.getCityDetailsByName("Fantasía",false));
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
        assertThrows(IllegalArgumentException.class, () -> cityService.isAirportInCity("Desconocida", "Aeropuerto X"));
    }

    @Test
    @DisplayName("GIVEN a list of cities WHEN getAllCities THEN return the list of city names")
    void getAllCities_shouldReturnListOfCityNames() {
        cityService.createCity(crearCiudadBasica("Ciudad1"));
        cityService.createCity(crearCiudadBasica("Ciudad2"));
        cityService.createCity(crearCiudadBasica("Ciudad3"));

        var cities = cityService.getAllCitiesNames();

        assertEquals(3, cities.size());
        assertTrue(cities.contains("Ciudad1"));
        assertTrue(cities.contains("Ciudad2"));
        assertTrue(cities.contains("Ciudad3"));
    }

    @Test
    @DisplayName("GIVEN no cities WHEN getAllCities THEN return empty list")
    void getAllCities_shouldReturnEmptyListIfNoCities() {
        var cities = cityService.getAllCitiesNames();

        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }
}
