package domain.dtos.user;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AirlineDTO extends BaseAirlineDTO {
    private List<String> flightsNames;
    private List<String> flightRoutesNames;

    public AirlineDTO(String nickname, String name, String mail, String description, String web) {
        super(nickname, name, mail, description, web);
        this.flightsNames = new ArrayList<>();
        this.flightRoutesNames = new ArrayList<>();
    }
}