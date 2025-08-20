package shared.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;

public class ValidatorUtil {
    public static <T> void validate(T object) {
        Validator validator = ValidatorFactoryProvider.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + violations);
        }
    }
}
