package app.adapters.mappers;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static String fromLocalDateToString(LocalDate date) {
        return (date == null) ? null : FORMATTER.format(date);
    }

    public static LocalDate fromStringToLocalDate (String date) {
        return (date == null) ? null : LocalDate.parse(date, FORMATTER);
    }
}
