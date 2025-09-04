package domain.dtos.user;

import domain.models.enums.EnumTipoDocumento;
import lombok.*;

import java.time.LocalDate;
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
            String surname,
            String citizenship,
            LocalDate birthDate,
            String numDoc,
            EnumTipoDocumento docType
    ) {
        super(nickname, name, mail, surname, citizenship, birthDate, numDoc, docType);
        this.boughtPackagesIds = null;
        this.bookFlightsIds = null;
    }

    public CustomerDTO(
            String nickname,
            String name,
            String mail,
            String surname,
            String citizenship,
            LocalDate birthDate,
            String numDoc,
            EnumTipoDocumento docType,
            List<Long> boughtPackagesIds,
            List<Long> bookFlightsIds
    ) {
        super(nickname, name, mail, surname, citizenship, birthDate, numDoc, docType);
        this.boughtPackagesIds = boughtPackagesIds;
        this.bookFlightsIds = bookFlightsIds;
    }
}
