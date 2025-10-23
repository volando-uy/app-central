package domain.models.user;

import domain.dtos.user.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User {


    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Id
    @NotBlank
    @Size(min = 2, max = 50)
    private String nickname;

    @NotBlank
    @Email
    private String mail;

    @NotBlank
    @Size(min = 6, max = 70)
    private String password;

    private String image;

    public abstract void updateDataFrom(UserDTO newData);
}