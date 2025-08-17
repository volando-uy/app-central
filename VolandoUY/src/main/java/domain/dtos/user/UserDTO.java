package domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.annotations.Required;
import shared.constants.CTCliente;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserDTO {
    @Required(label = CTCliente.CT_NAME)
    private String name;
    @Required(label = CTCliente.CT_NICKNAME)
    private String nickname;
    @Required(label = CTCliente.CT_MAIL)
    private String mail;
}