package domain.dtos.user;

import domain.models.enums.EnumTipoDocumento;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO extends UserDTO {

    private String surname;
    private String citizenship;
    private LocalDate birthDate;
    private String numDoc; // corregido
    private EnumTipoDocumento docType; // corregido

    public CustomerDTO(
            String nickname,
            String name,
            String email,
            String surname,
            String citizenship,
            LocalDate birthDate,
            String numDoc,
            EnumTipoDocumento docType
    ) {
        super(nickname, name, email);
        this.surname = surname;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.numDoc = numDoc;
        this.docType = docType;
    }
}
