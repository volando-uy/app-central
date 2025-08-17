package domain.dtos.user;


import lombok.Data;
import shared.annotations.Required;
import shared.constants.CTCliente;

@Data
public class AirlineDTO extends UserDTO {
    @Required(label = CTCliente.CT_DESCRIPTION)
    private String description;

// Es opcional este campo
    private String web;
}