package domain.models.user;


import domain.dtos.user.CategoryDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    private String surname;
    private LocalDate birthDate;
    private String citizenship;
    private EnumTipoDocumento idType;
    private String id;


    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof CategoryDTO newDataCasted)) return;

        this.setName(newDataCasted.getName());
        this.setSurname(newDataCasted.getSurname());
        this.setBirthDate(newDataCasted.getBirthDate());
        this.setId(newDataCasted.getId());
        this.setIdType(newDataCasted.getIdType());
        this.setCitizenship(newDataCasted.getCitizenship());

    }

}
