package app.adapters.dto.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoapBaseAirlineDTO extends SoapUserDTO {
    private String description;
    private String web;
    public SoapBaseAirlineDTO(
            String nickname,
            String name,
            String mail,
            String password,
            String image,
            String userType,
            String description,
            String web
    ) {
        super(nickname, name, mail, password, image, userType);
        this.description = description;
        this.web = web;
    }
}
