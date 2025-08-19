package domain.models.user;


import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {

    @NotBlank
    @Size(min = 2, max = 100)
    private String surname;

    @NotNull
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
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
