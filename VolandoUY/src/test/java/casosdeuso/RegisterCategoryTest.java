package casosdeuso;

import controllers.category.ICategoryController;
import domain.dtos.category.CategoryDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterCategoryTest {

    private ICategoryController categoryController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        categoryController = ControllerFactory.getCategoryController();
    }

    @Test
    @DisplayName("CU: Alta de categoría exitosa")
    void altaCategoriaExitosa() {
        // Paso 1: Crear una nueva categoría
        CategoryDTO nuevaCategoria = new CategoryDTO("Económico");

        CategoryDTO creada = categoryController.createCategory(nuevaCategoria);

        // Paso 2: Validar que fue creada correctamente
        assertNotNull(creada);
        assertEquals("Económico", creada.getName());

        // Paso 3: Validar que aparece en el listado
        List<String> categorias = categoryController.getAllCategoriesNames();
        assertTrue(categorias.contains("Económico"));
    }

    @Test
    @DisplayName("CU: No se permite crear categoría duplicada")
    void altaCategoriaDuplicada() {
        categoryController.createCategory(new CategoryDTO("Premium"));

        // Intentar crear la misma categoría
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            categoryController.createCategory(new CategoryDTO("Premium"));
        });

        assertEquals(String.format(ErrorMessages.ERR_CATEGORY_EXISTS, "Premium"), ex.getMessage());
    }
}
