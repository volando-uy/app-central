package domain.dtos.user;


import domain.dtos.flightRoute.FlightRouteDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.util.List;

@Getter
@Setter
public class AirlineDTO extends UserDTO {
    private String description;

    // Opcional
    private String web;

    private List<FlightRouteDTO> flightRoutes;

    public AirlineDTO() {
        super();
    }

    public AirlineDTO(String nickname, String name, String mail, String description, List<FlightRouteDTO> flightRoutes) {
        super(nickname, name, mail);
        this.description = description;
        this.web = ""; // Web is optional, so we initialize it to empty
        this.flightRoutes = flightRoutes;
    }

    public AirlineDTO(String nickname, String name, String mail, String description, String web, List<FlightRouteDTO> flightRoutes) {
        super(nickname, name, mail);
        this.description = description;
        this.web = web;
        this.flightRoutes = flightRoutes;
    }
}