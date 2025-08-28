package controllers.city;

import domain.dtos.city.CityDTO;
import domain.services.city.ICityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CityControllerTest {

    private ICityService cityService;
    private ICityController cityController;

    @BeforeEach
    void setUp() {
        cityService = mock(ICityService.class);
        cityController = new CityController(cityService);
    }

    @Test
    @DisplayName("GIVEN valid CityDTO WHEN createCity is called THEN it should delegate and return the DTO")
    void createCity_shouldCallServiceAndReturnDTO() {
        // GIVEN
        CityDTO cityDTO = new CityDTO("Montevideo", "Uruguay", -34.9, -56.2, null);
        when(cityService.createCity(cityDTO)).thenReturn(cityDTO);

        // WHEN
        CityDTO result = cityController.createCity(cityDTO);

        // THEN
        assertNotNull(result);
        assertEquals("Montevideo", result.getName());
        verify(cityService).createCity(cityDTO);
    }

    @Test
    @DisplayName("GIVEN existing city name WHEN getCityByName is called THEN it should return correct CityDTO")
    void getCityByName_shouldReturnCorrectDTO() {
        // GIVEN
        CityDTO cityDTO = new CityDTO("Buenos Aires", "Argentina", -34.6, -58.4, null);
        when(cityService.getCityDetailsByName("Buenos Aires")).thenReturn(cityDTO);

        // WHEN
        CityDTO result = cityController.getCityByName("Buenos Aires");

        // THEN
        assertNotNull(result);
        assertEquals("Buenos Aires", result.getName());
        verify(cityService).getCityDetailsByName("Buenos Aires");
    }

    @Test
    @DisplayName("GIVEN a city name WHEN cityExists is called THEN it should return the correct boolean value")
    void cityExists_shouldReturnTrueOrFalse() {
        // GIVEN
        when(cityService.cityExists("Madrid")).thenReturn(true);
        when(cityService.cityExists("Atlantis")).thenReturn(false);

        // WHEN & THEN
        assertTrue(cityController.cityExists("Madrid"));
        assertFalse(cityController.cityExists("Atlantis"));

        verify(cityService).cityExists("Madrid");
        verify(cityService).cityExists("Atlantis");
    }

    @Test
    @DisplayName("GIVEN city and airport WHEN isAirportInCity is called THEN it should delegate and return boolean")
    void isAirportInCity_shouldDelegateAndReturnBoolean() {
        // GIVEN
        when(cityService.isAirportInCity("London", "Heathrow")).thenReturn(true);
        when(cityService.isAirportInCity("London", "Fake Airport")).thenReturn(false);

        // WHEN & THEN
        assertTrue(cityController.isAirportInCity("London", "Heathrow"));
        assertFalse(cityController.isAirportInCity("London", "Fake Airport"));

        verify(cityService).isAirportInCity("London", "Heathrow");
        verify(cityService).isAirportInCity("London", "Fake Airport");
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("GIVEN existing city name WHEN getCityDetailsByName is called THEN it should return correct CityDTO")
    void getCityDetailsByName_shouldReturnCorrectDTO() {
        // GIVEN
        CityDTO cityDTO = new CityDTO("Santiago", "Chile", -33.45, -70.66, null);
        when(cityService.getCityDetailsByName("Santiago")).thenReturn(cityDTO);

        // WHEN
        CityDTO result = cityController.getCityDetailsByName("Santiago");

        // THEN
        assertNotNull(result);
        assertEquals("Santiago", result.getName());
        assertEquals("Chile", result.getCountry());
        verify(cityService).getCityDetailsByName("Santiago");
        verifyNoMoreInteractions(cityService);
    }

    @Test
    @DisplayName("GIVEN service returns list WHEN getAllCities is called THEN it should return same list")
    void getAllCities_shouldReturnServiceList() {
        // GIVEN
        List<String> cities = Arrays.asList("Montevideo", "Buenos Aires", "Lima");
        when(cityService.getAllCities()).thenReturn(cities);

        // WHEN
        List<String> result = cityController.getAllCities();

        // THEN
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(cities, result);
        verify(cityService).getAllCities();
        verifyNoMoreInteractions(cityService);
    }
}
