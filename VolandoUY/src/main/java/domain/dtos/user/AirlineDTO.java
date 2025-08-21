package domain.dtos.user;


import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AirlineDTO extends UserDTO {
    private String description;

    // Opcional
    private String web;


    public AirlineDTO(String nickname, String name, String mail, String description) {
        super(nickname, name, mail);
        this.description = description;
        this.web = ""; // Web is optional, so we initialize it to empty
    }

    public AirlineDTO(String nickname, String name, String mail, String description, String web) {
        super(nickname, name, mail);
        this.description = description;
        this.web = web;
    }
}