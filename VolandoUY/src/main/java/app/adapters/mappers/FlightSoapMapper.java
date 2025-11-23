package app.adapters.mappers;

import app.adapters.dto.flights.BaseFlightSoapViewDTO;
import domain.dtos.flight.BaseFlightDTO;

import java.time.format.DateTimeFormatter;

public class FlightSoapMapper {
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static BaseFlightSoapViewDTO mapToSoapView(BaseFlightDTO flight) {
        BaseFlightSoapViewDTO dto = new BaseFlightSoapViewDTO();

        dto.setName(flight.getName());
        dto.setDuration(flight.getDuration());
        dto.setMaxEconomySeats(flight.getMaxEconomySeats());
        dto.setMaxBusinessSeats(flight.getMaxBusinessSeats());
        dto.setImage(flight.getImage());

        if (flight.getDepartureTime() != null) {
            dto.setDepartureTime(flight.getDepartureTime().format(ISO_FORMAT));
        }

        if (flight.getCreatedAt() != null) {
            dto.setCreatedAt(flight.getCreatedAt().format(ISO_FORMAT));
        }

        return dto;
    }
}
