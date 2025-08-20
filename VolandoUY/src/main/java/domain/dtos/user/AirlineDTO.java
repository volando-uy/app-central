package domain.dtos.user;


import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AirlineDTO extends UserDTO {
    private String description;

    // Opcional
    private String web;

    private List<FlightRouteDTO> flightRoutes;
    private List<Flight> flights;
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
    public void addFlightRoute(FlightRouteDTO flightRoute) {
        if(flightRoutes == null) {
            flightRoutes = new ArrayList<>();
        }
        flightRoutes.add(flightRoute);
    }
    public void addFlight(Flight flight) {
        if(flights == null) {
            flights = new ArrayList<>();
        }
        flights.add(flight);
    }
}