package shared.utils;

import shared.constants.ErrorMessages;
import shared.constants.RegexPatterns;

import java.time.LocalDate;

public class ValidationUtils {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(RegexPatterns.EMAIL);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static void validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CAMPO_OBLIGATORIO,fieldName));
        }
    }


}
