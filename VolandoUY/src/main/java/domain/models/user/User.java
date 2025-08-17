package domain.models.user;

import domain.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {
    private String name;
    private String nickname;
    private String mail;

    public abstract void updateDataFrom(UserDTO newData);

}
