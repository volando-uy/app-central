package domain.dtos.airport;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AirportDTO extends BaseAirportDTO {
    private String cityName;

    public AirportDTO(String name, String code) {
        super(name, code);
        this.cityName = null;
    }
}
