package domain.dtos.user;

import domain.models.user.enums.EnumTipoDocumento;
import lombok.*;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.time.LocalDate;

@Getter
@Setter
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

    public CustomerDTO() {
        super();
    }

    public CustomerDTO(String username, String password, String email, String surname, String citizenship, LocalDate birthDate, String id, EnumTipoDocumento idType) {
        super(username, password, email);
        this.surname = surname;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.id = id;
        this.idType = idType;
    }

}