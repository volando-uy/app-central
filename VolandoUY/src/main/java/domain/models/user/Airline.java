package domain.models.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.UserDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import factory.FactoryController;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airline extends User {

    private ModelMapper modelMapper = FactoryController.getModelMapper();

    @NotBlank
    @Size(min = 10, max = 500)
    private String description;

    @Pattern(regexp = "(^$)|(^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$)", message = "El formato de la web no es válido")
    private String web;

    private List<Flight> flights= new ArrayList<>();

    private List<FlightRoute> flightRoutes=new ArrayList<>();

    public Airline(String nickname, String name, String mail, String description, String web) {
        super(nickname, name, mail);
        this.description = description;
        this.web = web;
        this.flights = new ArrayList<>();
        this.flightRoutes = new ArrayList<>();
    }


    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof AirlineDTO newDataCasted)) return;

        this.setName(newDataCasted.getName());
        this.setDescription(newDataCasted.getDescription());
        this.setWeb(newDataCasted.getWeb());
    }

    @Override
    public String toString() {
        return "Airline{" +
                "description='" + description + '\'' +
                ", web='" + web + '\'' +
                ", flights=" + flights +
                ", flightRoutes=" + (flightRoutes == null ? "[]" :
                flightRoutes.stream()
                        .map(FlightRoute::getName)
                        .toList()) +
                '}';
    }

}
