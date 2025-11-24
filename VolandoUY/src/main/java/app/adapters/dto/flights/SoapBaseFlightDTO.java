package app.adapters.dto.flights;

import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlType(name = "soapBaseFlightDTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoapBaseFlightDTO {
    private String name;
    private String departureTime; // en formato ISO
    private Long duration;
    private Integer maxEconomySeats;
    private Integer maxBusinessSeats;
    private String createdAt; // en formato ISO
    private String image;

}