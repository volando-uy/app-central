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


    @OneToMany(mappedBy = "customer")
    private List<BuyPackage> boughtPackages;


    //1 cliente reserva muchos vuelos
    @OneToMany(mappedBy = "customer")
    private List<BookFlight> bookedFlights;






    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof CustomerDTO newDataCasted)) return;

//        this.setName(newDataCasted.getName());
//        this.setSurname(newDataCasted.getSurname());
//        this.setBirthDate(newDataCasted.getBirthDate());
//        this.setDocType(newDataCasted.getDocType());
//        this.setNumDoc(newDataCasted.getNumDoc());
//        this.setCitizenship(newDataCasted.getCitizenship());
        if(newDataCasted.getName() != null && !newDataCasted.getName().isBlank())
            this.setName(newDataCasted.getName());
//        if(newDataCasted.getMail() != null && !newDataCasted.getMail().isBlank())
//            this.setMail(newDataCasted.getMail());
        if(newDataCasted.getSurname() != null && !newDataCasted.getSurname().isBlank())
            this.setSurname(newDataCasted.getSurname());
        if(newDataCasted.getBirthDate() != null )
            this.setBirthDate(newDataCasted.getBirthDate());
        if(newDataCasted.getDocType() != null )
            this.setDocType(newDataCasted.getDocType());
        if(newDataCasted.getNumDoc() != null && !newDataCasted.getNumDoc().isBlank())
            this.setNumDoc(newDataCasted.getNumDoc());
        if(newDataCasted.getCitizenship() != null && !newDataCasted.getCitizenship().isBlank())
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
                '}' + super.toString();
    }
}
