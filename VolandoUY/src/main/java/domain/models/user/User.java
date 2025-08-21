package domain.models.user;

import domain.dtos.user.UserDTO;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nickname;

    @NotBlank
    @Email
    private String mail;

    public abstract void updateDataFrom(UserDTO newData);
}