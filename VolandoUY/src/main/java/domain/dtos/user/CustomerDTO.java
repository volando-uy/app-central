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
    private String id;
    private EnumTipoDocumento idType;

    public CustomerDTO(String nickname, String name, String email, String surname, String citizenship, LocalDate birthDate, String id, EnumTipoDocumento idType) {
        super(nickname, name, email);
        this.surname = surname;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.id = id;
        this.idType = idType;
    }

}