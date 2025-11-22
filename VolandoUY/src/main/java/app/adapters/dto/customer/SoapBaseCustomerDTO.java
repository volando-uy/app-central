package app.adapters.dto.customer;

import app.adapters.dto.localdate.LocalDateWithValue;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import domain.models.enums.EnumTipoDocumento;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SoapBaseCustomerDTO {

    private String nickname;
    private String name;
    private String mail;
    private String password;
    private String image;

    private String surname;
    private String citizenship;
    private LocalDateWithValue birthDate;
    private String numDoc;
    private EnumTipoDocumento docType;

}