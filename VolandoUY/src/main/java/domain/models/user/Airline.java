package domain.models.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airline extends User {
    private String description;
    private String web;

    @Override
    public void updateDataFrom(UserDTO newData) {
        if (!(newData instanceof AirlineDTO newDataCasted)) return;

        this.setName(newDataCasted.getName());
        this.setDescription(newDataCasted.getDescription());
        this.setWeb(newDataCasted.getWeb());

    }
}
