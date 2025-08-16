package domain.dtos.user;


import domain.models.user.Aerolinea;
import jdk.jfr.Label;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.annotations.Required;
import shared.constants.CTCliente;

@Data
public class AerolineaDTO extends UsuarioDTO {
    @Required(label = CTCliente.CT_DESCRIPCION)
    private String descripcion;

// Es opcional este campo
    private String web;
}