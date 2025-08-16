package domain.dtos.user;

import lombok.Data;
import shared.annotations.Required;
import shared.constants.CTCliente;

@Data
public abstract class UsuarioDTO {
    @Required(label = CTCliente.CT_NOMBRE)
    private String nombre;
    @Required(label = CTCliente.CT_APELLIDO)
    private String nickname;
    @Required(label = CTCliente.CT_MAIL)
    private String mail;
}