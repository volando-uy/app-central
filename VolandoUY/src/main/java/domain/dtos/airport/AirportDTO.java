package domain.dtos.airport;

import domain.models.city.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {
    private City city;
    private String name;
    private String code;
}
