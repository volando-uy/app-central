package domain.dtos.ticket;

import domain.models.enums.EnumTipoAsiento;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseTicketDTO {
    private Long id;
    private String name;
    private String surname;
    private String numDoc;
}
