package domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.constants.Images;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserDTO {
    private String nickname;
    private String name;
    private String mail;
    private String password;
    private String image = Images.USER_DEFAULT;
}