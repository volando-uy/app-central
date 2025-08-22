package controllers.category;

import domain.dtos.category.CategoryDTO;
import domain.services.category.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    private ICategoryService categoryService;
    private ICategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryService = mock(ICategoryService.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    @DisplayName("GIVEN valid CategoryDTO WHEN createCategory is called THEN it should delegate and return the DTO")
    void createCategory_shouldReturnCreatedCategory() {
        // GIVEN
        CategoryDTO inputDTO = new CategoryDTO("Turismo");
        when(categoryService.createCategory(inputDTO)).thenReturn(inputDTO);

        // WHEN
        CategoryDTO result = categoryController.createCategory(inputDTO);

        // THEN
        assertNotNull(result);
        assertEquals("Turismo", result.getName());
        verify(categoryService).createCategory(inputDTO);
    }

    @Test
    @DisplayName("GIVEN existing category name WHEN getCategoryByName is called THEN correct DTO should be returned")
    void getCategoryByName_shouldReturnCorrectDTO() {
        // GIVEN
        CategoryDTO expectedDTO = new CategoryDTO("Negocios");
        when(categoryService.getCategoryDetailsByName("Negocios")).thenReturn(expectedDTO);

        // WHEN
        CategoryDTO result = categoryController.getCategoryByName("Negocios");

        // THEN
        assertNotNull(result);
        assertEquals("Negocios", result.getName());
        verify(categoryService).getCategoryDetailsByName("Negocios");
    }

    @Test
    @DisplayName("GIVEN multiple categories WHEN getAllCategoriesNames is called THEN all names are returned")
    void getAllCategoriesNames_shouldReturnListOfNames() {
        // GIVEN
        List<String> expectedNames = List.of("Turismo", "Negocios", "Estudios");
        when(categoryService.getAllCategoriesNames()).thenReturn(expectedNames);

        // WHEN
        List<String> result = categoryController.getAllCategoriesNames();

        // THEN
        assertEquals(3, result.size());
        assertTrue(result.contains("Turismo"));
        verify(categoryService).getAllCategoriesNames();
    }
}
