package app.adapters.dto.flights;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@XmlType(name = "soapFlightDTO")
@Getter
@Setter
@NoArgsConstructor
public class SoapFlightDTO extends SoapBaseFlightDTO {
    private String airlineNickname;
    private String flightRouteName;

    public SoapFlightDTO(String name, String departureTime, Long duration, Integer maxEconomySeats, Integer maxBusinessSeats, String createdAt, String image) {
        super(name, departureTime, duration, maxEconomySeats, maxBusinessSeats, createdAt, image);
        this.airlineNickname = null;
        this.flightRouteName = null;
    }
}
