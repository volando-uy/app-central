package app.adapters.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeMapper {

    // 📅 ISO format: 2025-11-24T15:30:00
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public static LocalDateTime toLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isBlank()) return null;
        try {
            return LocalDateTime.parse(dateTimeStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid LocalDateTime format: " + dateTimeStr, e);
        }
    }

    public static String toString(LocalDateTime dateTime) {
        return (dateTime != null) ? FORMATTER.format(dateTime) : null;
    }
}
