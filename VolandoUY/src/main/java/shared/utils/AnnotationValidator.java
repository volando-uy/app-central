package shared.utils;



import shared.annotations.Required;
import shared.constants.ErrorMessages;

import java.lang.reflect.Field;

//Java Reflection
public class AnnotationValidator {

    public static void validateRequiredFields(Object dto) {
        Class<?> dtoClass = dto.getClass();

        while (dtoClass != null) {
            for (Field field : dtoClass.getDeclaredFields()) {
                Required annotation = field.getAnnotation(Required.class);
                if (annotation != null) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(dto);
                        if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
                            String label = annotation.label().isEmpty() ? field.getName() : annotation.label();
                            throw new IllegalArgumentException(
                                    String.format(ErrorMessages.ERR_CAMPO_OBLIGATORIO, label)
                            );
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                String.format(ErrorMessages.ERR_ACCEDER_CAMPO, field.getName()), e
                        );
                    }
                }
            }
            dtoClass = dtoClass.getSuperclass(); // sube un nivel en la jerarquía, para recorrer tambien al padre
        }
    }
}