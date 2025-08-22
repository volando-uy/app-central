package controllers.city;

import domain.dtos.city.CityDTO;
import domain.services.city.ICityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
