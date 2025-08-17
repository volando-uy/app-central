package domain.dtos.user;

import domain.models.user.enums.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends UserDTO {

    @Required(label = CTCliente.CT_SURNAME)
    private String surname;

    @Required(label = CTCliente.CT_CITIZENSHIP)
    private String citizenship;

    @Required(label = CTCliente.CT_BIRTH_DATE)
    private LocalDate birthDate;

    @Required(label = CTCliente.CT_ID)
    private String id;

    @Required(label = CTCliente.CT_ID_TYPE)
    private EnumTipoDocumento idType;

}