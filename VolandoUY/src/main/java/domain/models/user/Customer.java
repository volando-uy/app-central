package domain.models.user;


import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.enums.EnumTipoDocumento;
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

    @ManyToMany
    private List<FlightRoutePackage> boughtPackages;

    @NotBlank
    @Size(min = 2, max = 100)
    private String surname;

    @NotNull
    @Past(message = ErrorMessages.ERR_BIRTHDAY_IN_PAST)
    private LocalDate birthDate;

    @NotBlank
    private String citizenship;

    @NotNull
    private EnumTipoDocumento idType;

    @NotBlank
    private String id;

    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof CustomerDTO newDataCasted)) return;

        this.setName(newDataCasted.getName());
        this.setSurname(newDataCasted.getSurname());
        this.setBirthDate(newDataCasted.getBirthDate());
        this.setId(newDataCasted.getId());
        this.setIdType(newDataCasted.getIdType());
        this.setCitizenship(newDataCasted.getCitizenship());

    }

}
