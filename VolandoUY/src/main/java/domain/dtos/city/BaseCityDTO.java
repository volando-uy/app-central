package domain.dtos.city;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCityDTO {
    private String name;
    private String country;
    private double latitude;
    private double longitude;

}
