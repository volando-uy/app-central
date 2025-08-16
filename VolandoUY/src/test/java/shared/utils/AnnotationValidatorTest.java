package shared.utils;

import org.junit.jupiter.api.Test;
import shared.annotations.Required;
import shared.constants.ErrorMessages;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationValidatorTest {

    static class SimpleDTO {
        @Required(label = "Nombre")
        private String nombre;

        public SimpleDTO(String nombre) {
            this.nombre = nombre;
        }
    }

    static class HeredadoDTO extends BaseDTO {
        @Required(label = "Email")
        private String email;

        public HeredadoDTO(String nombre, String email) {
            super(nombre);
            this.email = email;
        }
    }

    static class BaseDTO {
        @Required(label = "Nombre")
        private String nombre;

        public BaseDTO(String nombre) {
            this.nombre = nombre;
        }
    }

    @Test
    void validateRequiredFields_conCampoValido_noLanzaExcepcion() {
        SimpleDTO dto = new SimpleDTO("Jose");
        assertDoesNotThrow(() -> AnnotationValidator.validateRequiredFields(dto));
    }

    @Test
    void validateRequiredFields_conCampoNulo_lanzaExcepcion() {
        SimpleDTO dto = new SimpleDTO(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AnnotationValidator.validateRequiredFields(dto));
        assertTrue(ex.getMessage().contains("Nombre"));
    }

    @Test
    void validateRequiredFields_conCampoVacio_lanzaExcepcion() {
        SimpleDTO dto = new SimpleDTO("   ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AnnotationValidator.validateRequiredFields(dto));
        assertTrue(ex.getMessage().contains("Nombre"));
    }

    @Test
    void validateRequiredFields_conHerenciaValida_noLanzaExcepcion() {
        HeredadoDTO dto = new HeredadoDTO("Gabriel", "correo@ejemplo.com");
        assertDoesNotThrow(() -> AnnotationValidator.validateRequiredFields(dto));
    }

    @Test
    void validateRequiredFields_conHerenciaYCampoNulo_lanzaExcepcion() {
        HeredadoDTO dto = new HeredadoDTO(null, "correo@ejemplo.com");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AnnotationValidator.validateRequiredFields(dto));
        assertTrue(ex.getMessage().contains("Nombre"));
    }
}
