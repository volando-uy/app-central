package domain.models.airport;

import domain.models.city.City;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    private City city;

    @NotBlank(message = "El nombre del aeropuerto es obligatorio")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "El código IATA es obligatorio")
    @Pattern(regexp = "^[A-Z]{3}$", message = "El código IATA debe ser 3 letras mayúsculas")
    private String code;

    @Override
    public String toString() {
        return "Airport{name=" + name + ", code=" + code + "}";
    }

    public Airport(String name, String code) {
        this.name = name;
        this.code = code;
        this.city = null;
    }

}
