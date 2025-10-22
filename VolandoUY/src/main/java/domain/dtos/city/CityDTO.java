package domain.dtos.city;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CityDTO extends BaseCityDTO {
    private List<String> airportNames;

    public CityDTO(String name, String country, double latitude, double longitude) {
        super(name, country, latitude, longitude);
        this.airportNames = null;
    }
}
