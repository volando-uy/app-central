package domain.services.flightRoute;

import domain.dtos.flightRoute.CategoryDTO;
import domain.models.flightRoute.Category;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.naming.ldap.Control;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FlightRouteServiceTest {

    private IFlightRouteService flightRouteService;

    private ModelMapper modelMapper;

    @BeforeEachw
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