package app.adapters.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateMapper {

    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,                      // yyyy-MM-dd
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),             // 24/11/2025
            DateTimeFormatter.ofPattern("MM-dd-yyyy")              // 11-24-2025
    };


    public static LocalDate toLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Invalid date format: " + dateStr);
    }
    public static String toString(LocalDate date) {
        if (date == null) return null;
        return FORMATTERS[0].format(date); // Default to ISO format
    }
}
