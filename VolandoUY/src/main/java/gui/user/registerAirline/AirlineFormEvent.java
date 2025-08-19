package gui.user.registerAirline;

import domain.models.user.enums.EnumTipoDocumento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.EventObject;

@Getter
@Setter
public class AirlineFormEvent extends EventObject {
    private String nickname;
    private String name;
    private String mail;
    private String description;
    private String web;

    public AirlineFormEvent(Object source) {
        super(source);
    }

    public AirlineFormEvent(Object source, String nickname, String name, String mail, String description, String web) {
        super(source);
        this.nickname = nickname;
        this.name = name;
        this.mail = mail;
        this.description = description;
        this.web = web;
    }
}
