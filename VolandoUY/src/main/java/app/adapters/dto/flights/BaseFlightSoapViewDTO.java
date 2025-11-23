package app.adapters.dto.flights;

import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@XmlType(name = "baseFlightSoapViewDTO")
@Data
public class BaseFlightSoapViewDTO {
    private String name;
    private String departureTime; // en formato ISO
    private Long duration;
    private Integer maxEconomySeats;
    private Integer maxBusinessSeats;
    private String createdAt; // en formato ISO
    private String image;

}