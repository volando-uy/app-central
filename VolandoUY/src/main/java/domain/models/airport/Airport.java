package domain.models.airport;

import domain.models.city.City;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Airport {

    @Id
    @NotBlank(message = ErrorMessages.ERR_AIRPORT_CODE_MANDATORY)
    @Pattern(regexp = "^[A-Z]{3}$", message = ErrorMessages.ERR_AIRPORT_CODE_FORMAT)
    private String code;

    @NotBlank(message = ErrorMessages.ERR_AIRPORT_NAME_MANDATORY)
    @Size(min = 2, max = 100)
    private String name;


    @ManyToOne()
    private City city;



    public Airport(String name, String code) {
        this.name = name;
        this.code = code;
        this.city = null;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "city=" + city.getName() +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
