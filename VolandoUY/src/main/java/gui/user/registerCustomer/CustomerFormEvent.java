package gui.user.registerCustomer;

import domain.models.user.enums.EnumTipoDocumento;
import lombok.Getter;
import lombok.Setter;

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

    public CustomerFormEvent(Object source, String nickname, String name, String surname, String mail,
                             String citizenship, LocalDate birthDate, String id, EnumTipoDocumento idType) {
        super(source);
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.id = id;
        this.idType = idType;
    }
}
