package shared.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Nested
    @DisplayName("Tests de isValidEmail")
    class IsValidEmailTests {
        @Test
        @DisplayName("Email válido")
        void dadoEmailValido_retornaTrue() {
            assertTrue(ValidationUtils.isValidEmail("correo@example.com"));
        }

        @Test
        @DisplayName("Emails inválidos")
        void dadoEmailInvalido_retornaFalse() {
            assertFalse(ValidationUtils.isValidEmail("correo@mal"));
            assertFalse(ValidationUtils.isValidEmail("correo"));
            assertFalse(ValidationUtils.isValidEmail("correo@.com"));
            assertFalse(ValidationUtils.isValidEmail(""));
            assertFalse(ValidationUtils.isValidEmail(null));
        }
    }

    @Nested
    @DisplayName("Tests de isNullOrEmpty")
    class IsNullOrEmptyTests {
        @Test
        @DisplayName("null debe retornar true")
        void dadoNull_retornaTrue() {
            assertTrue(ValidationUtils.isNullOrEmpty(null));
        }

        @Test
        @DisplayName("String vacío o espacios retorna true")
        void dadoStringVacio_retornaTrue() {
            assertTrue(ValidationUtils.isNullOrEmpty("   "));
        }

        @Test
        @DisplayName("Texto válido retorna false")
        void dadoTextoValido_retornaFalse() {
            assertFalse(ValidationUtils.isNullOrEmpty("algo"));
        }
    }

    @Nested
    @DisplayName("Tests de validateRequired")
    class ValidateRequiredTests {
        @Test
        @DisplayName("Valor válido no lanza excepción")
        void dadoValorValido_noLanzaExcepcion() {
            assertDoesNotThrow(() -> ValidationUtils.validateRequired("texto", "campo"));
        }

        @Test
        @DisplayName("null lanza excepción con nombre del campo")
        void dadoNull_lanzaExcepcion() {
            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtils.validateRequired(null, "email")
            );
            assertTrue(ex.getMessage().contains("email"));
        }

        @Test
        @DisplayName("String vacío lanza excepción con nombre del campo")
        void dadoStringVacio_lanzaExcepcion() {
            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    ValidationUtils.validateRequired("   ", "nombre")
            );
            assertTrue(ex.getMessage().contains("nombre"));
        }
    }
}
