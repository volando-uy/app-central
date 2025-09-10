package domain.models.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.UserDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.constants.ErrorMessages;
import shared.constants.RegexPatterns;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airline extends User {

    @NotBlank
    @Size(min = 10, max = 500)
    private String description;

    @Pattern(regexp = RegexPatterns.WEB_URL, message = ErrorMessages.ERR_WEB_FORMAT)
    private String web;

    // RELACIÓN BIDIRECCIONAL CORRECTA: Flight -> Airline
    @OneToMany(mappedBy = "airline")
    private List<Flight> flights = new ArrayList<>();

    // RELACIÓN BIDIRECCIONAL CORRECTA: FlightRoute -> Airline
    @OneToMany(mappedBy = "airline")
    private List<FlightRoute> flightRoutes = new ArrayList<>();

    public Airline(String nickname, String name, String mail, String description, String web) {
        super(nickname, name, mail);
        this.description = description;
        this.web = web;
    }

    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof AirlineDTO newDataCasted)) return;
        if (newDataCasted.getName() != null && !newDataCasted.getName().isBlank())
            this.setName(newDataCasted.getName());
//        if (newDataCasted.getMail() != null && !newDataCasted.getMail().isBlank())
//            this.setMail(newDataCasted.getMail());
        if (newDataCasted.getDescription() != null && !newDataCasted.getDescription().isBlank())
            this.setDescription(newDataCasted.getDescription());
        if (newDataCasted.getWeb() != null && !newDataCasted.getWeb().isBlank())
            this.setWeb(newDataCasted.getWeb());

    }

    @Override
    public String toString() {
        return "Airline{" +
                "description='" + description + '\'' +
                ", web='" + web + '\'' +
                "} " + super.toString();
    }
}
