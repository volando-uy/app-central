package domain.dtos.user;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shared.annotations.Required;
import shared.constants.CTCliente;

@Getter
@Setter
public class AirlineDTO extends UserDTO {
    private String description;

    // Opcional
    private String web;

    public AirlineDTO() {
        super();
    }

    public AirlineDTO(String nickname, String name, String mail, String description) {
        super(nickname, name, mail);
        this.description = description;
        this.web = ""; // Web is optional, so we initialize it to empty
    }

    public AirlineDTO(String nickname, String name, String mail, String description, String web) {
        super(nickname, name, mail);
        this.description = description;
        this.web = web;
    }
}