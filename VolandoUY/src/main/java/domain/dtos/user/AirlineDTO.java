package domain.dtos.user;


import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineDTO extends UserDTO {
    private String description;

    private String web;

    private List<String> flightsNames;
    private List<String> flightRoutesNames;

    public AirlineDTO(String nickname, String name, String mail, String description, String web) {
        super(nickname, name, mail);
        this.description = description;
        this.web = web;
        this.flightsNames = new ArrayList<>();
        this.flightRoutesNames = new ArrayList<>();
    }

    public AirlineDTO(String nickname, String name, String mail, String description) {
        super(nickname, name, mail);
        this.description = description;
        this.web = "";
        this.flightsNames = new ArrayList<>();
        this.flightRoutesNames = new ArrayList<>();
    }

}