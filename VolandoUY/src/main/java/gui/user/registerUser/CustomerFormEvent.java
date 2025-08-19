package gui.user.registerUser;

import domain.dtos.user.CustomerDTO;
import domain.models.user.Customer;
import domain.models.user.enums.EnumTipoDocumento;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.time.LocalDate;
import java.util.EventObject;

@Getter
@Setter
public class CustomerFormEvent extends EventObject {
    private String name;
    private String nickname;
    private String mail;
    private String surname;
    private String citizenship;
    private LocalDate birthDate;
    private String id;
    private EnumTipoDocumento idType;

    public CustomerFormEvent(Object source) {
        super(source);
    }

    public CustomerFormEvent(Object source, String name, String nickname, String mail, String surname,
                             String citizenship, LocalDate birthDate, String id, EnumTipoDocumento idType) {
        super(source);
        this.name = name;
        this.nickname = nickname;
        this.mail = mail;
        this.surname = surname;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.id = id;
        this.idType = idType;
    }
}
