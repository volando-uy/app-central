//package shared.utils;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ValidationUtilsTest {
//
//    @Nested
//    @DisplayName("Tests de isValidEmail")
//    class IsValidEmailTests {
//        @Test
//        @DisplayName("Email válido")
//        void givenValidMail_returnTrue() {
//            assertTrue(ValidationUtils.isValidEmail("correo@example.com"));
//        }
//
//        @Test
//        @DisplayName("Emails inválidos")
//        void givenInvalidMail_returnFalse() {
//            assertFalse(ValidationUtils.isValidEmail("correo@mal"));
//            assertFalse(ValidationUtils.isValidEmail("correo"));
//            assertFalse(ValidationUtils.isValidEmail("correo@.com"));
//            assertFalse(ValidationUtils.isValidEmail(""));
//            assertFalse(ValidationUtils.isValidEmail(null));
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests de isNullOrEmpty")
//    class IsNullOrEmptyTests {
//        @Test
//        @DisplayName("null debe retornar true")
//        void givenNull_returnTrue() {
//            assertTrue(ValidationUtils.isNullOrEmpty(null));
//        }
//
//        @Test
//        @DisplayName("String vacío o espacios retorna true")
//        void givenEmptyString_returnTrue() {
//            assertTrue(ValidationUtils.isNullOrEmpty("   "));
//        }
//
//        @Test
//        @DisplayName("Texto válido retorna false")
//        void givenValidText_returnFalse() {
//            assertFalse(ValidationUtils.isNullOrEmpty("algo"));
//        }
//    }
//
//    @Nested
//    @DisplayName("Tests de validateRequired")
//    class ValidateRequiredTests {
//        @Test
//        @DisplayName("Valor válido no lanza excepción")
//        void givenValidValue_doesntThrowException() {
//            assertDoesNotThrow(() -> ValidationUtils.validateRequired("texto", "campo"));
//        }
//
//        @Test
//        @DisplayName("null lanza excepción con nombre del campo")
//        void givenNull_throwException() {
//            Exception ex = assertThrows(IllegalArgumentException.class, () ->
//                    ValidationUtils.validateRequired(null, "email")
//            );
//            assertTrue(ex.getMessage().contains("email"));
//        }
//
//        @Test
//        @DisplayName("String vacío lanza excepción con nombre del campo")
//        void givenEmptyString_throwException() {
//            Exception ex = assertThrows(IllegalArgumentException.class, () ->
//                    ValidationUtils.validateRequired("   ", "nombre")
//            );
//            assertTrue(ex.getMessage().contains("nombre"));
//        }
//    }
//}
