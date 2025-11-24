package app.adapters.dto.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import domain.models.enums.EnumTipoDocumento;
import lombok.*;

@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SoapBaseCustomerDTO extends SoapUserDTO {
    private String surname;
    private String citizenship;
    private String birthDate;
    private String numDoc;
    private EnumTipoDocumento docType;
    public SoapBaseCustomerDTO(
            String nickname,
            String name,
            String mail,
            String password,
            String image,
            String userType,
            String surname,
            String citizenship,
            String birthDate,
            String numDoc,
            EnumTipoDocumento docType
    ) {
        super(nickname, name, mail, password, image, userType);
        this.surname = surname;
        this.citizenship = citizenship;
        this.birthDate = birthDate;
        this.numDoc = numDoc;
        this.docType = docType;
    }

}