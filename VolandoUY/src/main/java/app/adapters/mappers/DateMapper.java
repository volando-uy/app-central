package app.adapters.mappers;

import app.adapters.dto.localdate.LocalDateWithValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static LocalDateWithValue toSoapLocalDate(LocalDate date) {
        return (date == null) ? null : new LocalDateWithValue(FORMATTER.format(date));
    }

    public static LocalDate fromSoapLocalDate(LocalDateWithValue wrapper) {
        return (wrapper == null || wrapper.getValue() == null) ? null : LocalDate.parse(wrapper.getValue(), FORMATTER);
    }
}
