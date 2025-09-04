package domain.dtos.user;

import domain.models.enums.EnumTipoDocumento;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BaseCustomerDTO extends UserDTO {
    private String surname;
    private String citizenship;
    private LocalDate birthDate;
    private String numDoc;
    private EnumTipoDocumento docType;

    public BaseCustomerDTO(
            String nickname,
            String name,
            String mail,
            String surname,
            String citizenship,
            LocalDate birthDate,
            String numDoc,
            EnumTipoDocumento docType
    ) {
        super(nickname, name, mail);
        this.surname = surname;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.numDoc = numDoc;
        this.docType = docType;
    }
}
