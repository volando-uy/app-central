package app.adapters.dto.user;

import app.adapters.dto.localdate.LocalDateWithValue;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.enums.EnumTipoDocumento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoapCustomerDTO extends SoapUserDTO {
    private String surname;
    private String citizenship;
    private LocalDateWithValue birthDate;
    private String numDoc;
    private EnumTipoDocumento docType;
}
