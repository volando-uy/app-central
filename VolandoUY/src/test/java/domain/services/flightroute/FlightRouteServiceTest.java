package domain.services.flightroute;

import domain.dtos.flightRoute.CategoryDTO;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FlightRouteServiceTest {

    private IFlightRouteService flightRouteService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = ControllerFactory.getModelMapper();
        // ESTO VA CON NEW PORQUE SINO SE ROMPE ENTRE TESTS!!!!
        flightRouteService = new FlightRouteService(modelMapper);
    }

    @Test
    @DisplayName("Should create and store a category from DTO")
    void createCategory_shouldAddCategoryToList() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("TestCategory");

        flightRouteService.createCategory(dto);

        assertEquals(1, flightRouteService.getAllCategories().size());
        CategoryDTO created = flightRouteService.getAllCategories().get(0);
        assertEquals("TestCategory", created.getName());
    }

    @Test
    @DisplayName("Should create and store a category from DTO")
    void creatasdqweasdqwe() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Test2");

        flightRouteService.createCategory(dto);

        assertEquals(1, flightRouteService.getAllCategories().size());
        CategoryDTO created = flightRouteService.getAllCategories().get(0);
        assertEquals("Test2", created.getName());
    }
}