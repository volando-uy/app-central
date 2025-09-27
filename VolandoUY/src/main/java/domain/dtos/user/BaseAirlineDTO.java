package domain.dtos.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseAirlineDTO extends UserDTO {
    private String description;
    private String web;

    public BaseAirlineDTO(
            String nickname,
            String name,
            String mail,
            String password,
            String image,
            String description,
            String web
    ) {
        super(nickname, name, mail, password, image);
        this.description = description;
        this.web = web;
    }
}
