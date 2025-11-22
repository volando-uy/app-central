package app.adapters.mappers;

import app.adapters.dto.localdatetime.LocalDateTimeWithValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeMapper {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static LocalDateTimeWithValue toSoapLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return new LocalDateTimeWithValue(ISO_FORMATTER.format(dateTime));
    }

    public static LocalDateTime fromSoapLocalDateTime(LocalDateTimeWithValue soapDateTime) {
        if (soapDateTime == null || soapDateTime.getValue() == null) return null;
        return LocalDateTime.parse(soapDateTime.getValue(), ISO_FORMATTER);
    }
}