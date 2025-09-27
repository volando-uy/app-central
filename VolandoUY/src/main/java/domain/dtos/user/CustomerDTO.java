package domain.dtos.user;

import domain.models.enums.EnumTipoDocumento;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO extends BaseCustomerDTO {
    private List<Long> boughtPackagesIds;
    private List<Long> bookFlightsIds;

    public CustomerDTO(
            String nickname,
            String name,
            String mail,
            String password,
            String image,
            String surname,
            String citizenship,
            LocalDate birthDate,
            String numDoc,
            EnumTipoDocumento docType
    ) {
        super(nickname, name, mail, password, image, surname, citizenship, birthDate, numDoc, docType);
        this.boughtPackagesIds = new ArrayList<>();
        this.bookFlightsIds = new ArrayList<>();
    }
}
