package app.adapters.mappers;

import app.adapters.dto.flights.SoapBaseFlightDTO;
import app.adapters.dto.flights.SoapFlightDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;

import java.time.format.DateTimeFormatter;

public class FlightSoapMapper {
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static SoapBaseFlightDTO mapToSoapBaseFlightDTO(BaseFlightDTO flight) {
        SoapBaseFlightDTO dto = new SoapBaseFlightDTO();

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
    public static BaseFlightDTO mapFromSoapBaseFlightDTO(SoapBaseFlightDTO soapDTO) {
        BaseFlightDTO dto = new BaseFlightDTO();

        dto.setName(soapDTO.getName());
        dto.setDuration(soapDTO.getDuration());
        dto.setMaxEconomySeats(soapDTO.getMaxEconomySeats());
        dto.setMaxBusinessSeats(soapDTO.getMaxBusinessSeats());
        dto.setImage(soapDTO.getImage());

        if (soapDTO.getDepartureTime() != null) {
            dto.setDepartureTime(java.time.LocalDateTime.parse(soapDTO.getDepartureTime(), ISO_FORMAT));
        }

        if (soapDTO.getCreatedAt() != null) {
            dto.setCreatedAt(java.time.LocalDateTime.parse(soapDTO.getCreatedAt(), ISO_FORMAT));
        }

        return dto;
    }
    public static SoapFlightDTO mapToSoapFlightDTO(FlightDTO flightDTO){
        SoapFlightDTO dto = new SoapFlightDTO();

        dto.setName(flightDTO.getName());
        dto.setDuration(flightDTO.getDuration());
        dto.setMaxEconomySeats(flightDTO.getMaxEconomySeats());
        dto.setMaxBusinessSeats(flightDTO.getMaxBusinessSeats());
        dto.setImage(flightDTO.getImage());

        if (flightDTO.getDepartureTime() != null) {
            dto.setDepartureTime(flightDTO.getDepartureTime().format(ISO_FORMAT));
        }

        if (flightDTO.getCreatedAt() != null) {
            dto.setCreatedAt(flightDTO.getCreatedAt().format(ISO_FORMAT));
        }

        dto.setAirlineNickname(flightDTO.getAirlineNickname());
        dto.setFlightRouteName(flightDTO.getFlightRouteName());

        return dto;
    }
}
