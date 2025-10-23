package utils;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import shared.utils.*;

import javax.swing.table.DefaultTableModel;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    // =================== PasswordManager ===================

    @Test
    void hashPassword_shouldReturnHash() {
        String hash = PasswordManager.hashPassword("password123");
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    void validatePassword_shouldReturnTrueForCorrectPassword() {
        String password = "secret";
        String hashed = PasswordManager.hashPassword(password);
        assertTrue(PasswordManager.validatePassword(password, hashed));
    }

    @Test
    void validatePassword_shouldReturnFalseForWrongPassword() {
        String password = "secret";
        String hashed = PasswordManager.hashPassword(password);
        assertFalse(PasswordManager.validatePassword("wrong", hashed));
    }

    @Test
    void validatePassword_shouldReturnFalseIfAnyInputIsNull() {
        assertFalse(PasswordManager.validatePassword(null, "123"));
        assertFalse(PasswordManager.validatePassword("abc", null));
    }

    // =================== CountryUtil ===================

    @Test
    void getAllCountries_shouldReturnListOfCountries() {
        List<String> countries = CountryUtil.getAllCountries();
        assertNotNull(countries);
        assertTrue(countries.size() > 10); // Deben ser más de 10 países
    }

    // =================== ValidatorFactoryProvider ===================

    @Test
    void validatorFactoryProvider_shouldReturnValidValidator() {
        assertNotNull(ValidatorFactoryProvider.getValidator());
    }

    // =================== NonEditableTableModel ===================

    @Test
    void nonEditableTableModel_shouldAlwaysReturnFalseForIsCellEditable() {
        String[] columns = {"Col1", "Col2"};
        NonEditableTableModel model = new NonEditableTableModel(columns, 2);
        assertFalse(model.isCellEditable(0, 0));
        assertFalse(model.isCellEditable(1, 1));
    }


    class Dummy {
        @NotNull
        private String value;

        public Dummy(String value) {
            this.value = value;
        }
    }

    @Test
    void validatorUtil_shouldThrowOnInvalidObject() {
        Dummy invalid = new Dummy(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ValidatorUtil.validate(invalid);
        });

        assertTrue(ex.getMessage().contains("Validation failed"));
    }
}
