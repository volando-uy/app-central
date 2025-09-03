package domain.dtos.city;

import domain.dtos.airport.AirportDTO;
import domain.models.airport.Airport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private String name;
    private String country;
    private double latitude;
    private double longitude;

    private List<String> airportNames;
}
