package shared.utils;

import org.junit.jupiter.api.Test;
import shared.annotations.Required;

import static org.junit.jupiter.api.Assertions.*;

class AnnotationValidatorTest {

    static class SimpleDTO {
        @Required(label = "Nombre")
        private String name;

        public SimpleDTO(String name) {
            this.name = name;
        }
    }

    static class InheritedDTO extends BaseDTO {
        @Required(label = "Email")
        private String email;

        public InheritedDTO(String name, String email) {
            super(name);
            this.email = email;
        }
    }

    static class BaseDTO {
        @Required(label = "Nombre")
        private String name;

        public BaseDTO(String name) {
            this.name = name;
        }
    }

    @Test
    void validateRequiredFields_withValidField_doesntThrowException() {
        SimpleDTO dto = new SimpleDTO("Jose");
        assertDoesNotThrow(() -> AnnotationValidator.validateRequiredFields(dto));
    }

    @Test
    void validateRequiredFields_withNullField_throwException() {
        SimpleDTO dto = new SimpleDTO(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AnnotationValidator.validateRequiredFields(dto));
        assertTrue(ex.getMessage().contains("Nombre"));
    }

    @Test
    void validateRequiredFields_withEmptyField_throwException() {
        SimpleDTO dto = new SimpleDTO("   ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AnnotationValidator.validateRequiredFields(dto));
        assertTrue(ex.getMessage().contains("Nombre"));
    }

    @Test
    void validateRequiredFields_withValidInheritance_doesntThrowException() {
        InheritedDTO dto = new InheritedDTO("Gabriel", "correo@ejemplo.com");
        assertDoesNotThrow(() -> AnnotationValidator.validateRequiredFields(dto));
    }

    @Test
    void validateRequiredFields_withInheritanceAndFieldNull_throwException() {
        InheritedDTO dto = new InheritedDTO(null, "correo@ejemplo.com");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AnnotationValidator.validateRequiredFields(dto));
        assertTrue(ex.getMessage().contains("Nombre"));
    }
}
