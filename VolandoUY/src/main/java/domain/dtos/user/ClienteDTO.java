package domain.dtos.user;

import domain.models.user.Cliente;
import domain.models.user.enums.EnumTipoDocumento;
import lombok.Data;
import shared.annotations.Required;
import shared.constants.CTCliente;

import java.time.LocalDate;

@Data
public class ClienteDTO extends UsuarioDTO {

    @Required(label = CTCliente.CT_APELLIDO)
    private String apellido;

    @Required(label = CTCliente.CT_NACIONALIDAD)
    private String nacionalidad;

    @Required(label = CTCliente.CT_FECHA_NACIMIENTO)
    private LocalDate fechaNacimiento;

    @Required(label = CTCliente.CT_NUMERO_DOCUMENTO)
    private String numDocumento;

    @Required(label = CTCliente.CT_TIPO_DOCUMENTO)
    private EnumTipoDocumento tipoDocumento;

}