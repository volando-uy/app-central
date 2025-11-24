package app.adapters.mappers;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeMapper {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String toSoapLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return ISO_FORMATTER.format(dateTime);
    }

    public static LocalDateTime fromSoapLocalDateTime(String soapDateTime) {
        if (soapDateTime == null) return null;
        return LocalDateTime.parse(soapDateTime, ISO_FORMATTER);
    }
}