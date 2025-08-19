package domain.dtos.city;

import domain.models.airport.Airport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private List<Airport> airports;
    private String name;
    private String country;
    private double latitude;
    private double longitude;
}
