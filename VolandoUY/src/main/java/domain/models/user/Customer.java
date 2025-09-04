package domain.models.user;


import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumTipoDocumento;
import domain.models.flight.Flight;
import domain.models.flightRoutePackage.FlightRoutePackage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.constants.ErrorMessages;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer")
public class Customer extends User {

    @NotBlank
    @Size(min = 2, max = 100)
    private String surname;



    @NotNull
    @Past(message = ErrorMessages.ERR_BIRTHDAY_IN_PAST)
    private LocalDate birthDate;

    @NotBlank
    private String citizenship;

    @NotNull
    private EnumTipoDocumento docType;

    @NotBlank
    private String numDoc;


    @ManyToMany()
    private List<BuyPackage> boughtPackages;


    //1 cliente reserva muchos vuelos
    @OneToMany()
    private List<BookFlight> bookedFlights;






    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof CustomerDTO newDataCasted)) return;

        this.setName(newDataCasted.getName());
        this.setSurname(newDataCasted.getSurname());
        this.setBirthDate(newDataCasted.getBirthDate());
        this.setDocType(newDataCasted.getDocType());
        this.setNumDoc(newDataCasted.getNumDoc());
        this.setCitizenship(newDataCasted.getCitizenship());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", citizenship='" + citizenship + '\'' +
                ", docType=" + docType +
                ", numDoc='" + numDoc + '\'' +
                ", boughtPackages=" + (boughtPackages != null ? boughtPackages.stream().map(buyPackage -> buyPackage.getFlightRoutePackage().getName()).toList() : "null") +
                ", bookedFlights=" + (bookedFlights != null ? bookedFlights.stream().map(BookFlight::getId).toList() : "null") +
                '}' + super.toString();
    }
}
