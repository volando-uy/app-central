package app.adapters.dto.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;
import shared.constants.Images;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoapUserDTO {
    private String nickname;
    private String name;
    private String mail;
    private String password;
    private String image = Images.USER_DEFAULT;
    private String userType;
}