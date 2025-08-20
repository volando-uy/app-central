package domain.models.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.UserDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import factory.ControllerFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airline extends User {

    private ModelMapper modelMapper = ControllerFactory.getModelMapper();

    @NotBlank
    @Size(min = 10, max = 500)
    private String description;

    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$", message = "El formato de la web no es válido")
    private String web;

    private List<Flight> flights=new ArrayList<>();

    private List<FlightRoute> flightRoutes=new ArrayList<>();


    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof AirlineDTO newDataCasted)) return;

        this.setName(newDataCasted.getName());
        this.setDescription(newDataCasted.getDescription());
        this.setWeb(newDataCasted.getWeb());

        if (newDataCasted.getFlightRoutes() != null) {
            this.flightRoutes = newDataCasted.getFlightRoutes().stream()
                    .map(dto -> modelMapper.map(dto, FlightRoute.class))
                    .collect(Collectors.toList());
            
        }
    }


    public void addFlightRoute(FlightRoute flightRoute) {
        if (flightRoutes == null) {
            flightRoutes = new java.util.ArrayList<>();
        }
        flightRoutes.add(flightRoute);
    }

    public void addFlight(Flight flight) {
        if (flights == null) {
            flights = new ArrayList<>();
        }
        flights.add(flight);
    }
}
