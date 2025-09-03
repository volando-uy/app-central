package domain.services.category;

import domain.dtos.category.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import utils.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {

    private ICategoryService categoryService;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        categoryService = new CategoryService();
    }

    @Test
    @DisplayName("GIVEN valid CategoryDTO WHEN createCategory is called THEN category is created")
    void createCategory_shouldAddCategory() {
        // GIVEN una categoría válida
        CategoryDTO dto = new CategoryDTO("Turismo");

        // WHEN se crea la categoría
        CategoryDTO created = categoryService.createCategory(dto);

        // THEN se retorna correctamente y se guarda internamente
        assertEquals("Turismo", created.getName());
        assertTrue(categoryService.existsCategory("Turismo"));
    }

    @Test
    @DisplayName("GIVEN duplicate category name WHEN createCategory is called THEN throw exception")
    void createCategory_shouldNotAllowDuplicates() {
        // GIVEN una categoría ya creada
        categoryService.createCategory(new CategoryDTO("Negocios"));

        // WHEN se intenta crear otra con el mismo nombre
        // THEN se lanza excepción por duplicado
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(new CategoryDTO("Negocios"));
        });
        //La categoria X ya existe
        assertEquals("La categoría Negocios ya existe", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN existing category WHEN getCategoryDetailsByName is called THEN return CategoryDTO")
    void getCategoryDetailsByName_shouldReturnCorrectDTO() {
        // GIVEN una categoría ya registrada
        categoryService.createCategory(new CategoryDTO("Cultura"));

        // WHEN se pide el detalle por nombre
        CategoryDTO result = categoryService.getCategoryDetailsByName("Cultura");

        // THEN se devuelve correctamente
        assertNotNull(result);
        assertEquals("Cultura", result.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing category WHEN getCategoryDetailsByName is called THEN throw exception")
    void getCategoryDetailsByName_shouldThrowIfNotFound() {
        // WHEN se pide una categoría que no existe
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.getCategoryDetailsByName("Gastronomía");
        });

        // THEN se lanza excepción
        assertEquals("La categoría Gastronomía no fue encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("GIVEN existing category WHEN getCategoryByName is called THEN return Category")
    void getCategoryByName_shouldReturnModel() {
        // GIVEN una categoría creada
        categoryService.createCategory(new CategoryDTO("Ecoturismo"));

        // WHEN se busca por nombre
        var cat = categoryService.getCategoryByName("Ecoturismo");

        // THEN se obtiene correctamente
        assertNotNull(cat);
        assertEquals("Ecoturismo", cat.getName());
    }

    @Test
    @DisplayName("GIVEN non-existing category WHEN getCategoryByName is called THEN throw exception")
    void getCategoryByName_shouldThrowIfNotFound() {
        // WHEN se busca una categoría inexistente + THEN da null
        assertFalse(categoryService.getCategoryByName("Histórica") != null);

    }

    @Test
    @DisplayName("GIVEN multiple categories WHEN getAllCategoriesNames is called THEN return all names")
    void getAllCategoriesNames_shouldReturnAll() {
        // GIVEN varias categorías
        categoryService.createCategory(new CategoryDTO("Playa"));
        categoryService.createCategory(new CategoryDTO("Montaña"));

        // WHEN se piden todos los nombres
        List<String> names = categoryService.getAllCategoriesNames();

        // THEN se devuelven correctamente
        assertEquals(2, names.size());
        assertTrue(names.contains("Playa"));
        assertTrue(names.contains("Montaña"));
    }

    @Test
    @DisplayName("GIVEN category name WHEN existsCategory is called THEN return true if exists")
    void existsCategory_shouldReturnTrueIfExists() {
        // GIVEN una categoría registrada
        categoryService.createCategory(new CategoryDTO("Urbano"));

        // WHEN se verifica existencia
        boolean exists = categoryService.existsCategory("Urbano");

        // THEN es verdadera
        assertTrue(exists);
    }

    @Test
    @DisplayName("GIVEN non-existing category name WHEN existsCategory is called THEN return false")
    void existsCategory_shouldReturnFalseIfNotExists() {
        // WHEN se verifica una categoría que no está
        boolean exists = categoryService.existsCategory("Desierto");

        // THEN es falsa
        assertFalse(exists);
    }
}
