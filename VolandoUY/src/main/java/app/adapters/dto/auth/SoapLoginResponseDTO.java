package app.adapters.dto.auth;

import app.adapters.dto.user.SoapUserDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SoapLoginResponseDTO {
    private String token;
    private SoapUserDTO user;
}