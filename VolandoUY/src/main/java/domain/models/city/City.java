package domain.models.city;

import domain.models.airport.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Valid // valida cada Airport dentro de la lista
    private List<@NotNull Airport> airports;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String country;

    @DecimalMin(value = "-90.0", inclusive = true, message = "Latitud mínima -90")
    @DecimalMax(value = "90.0", inclusive = true, message = "Latitud máxima 90")
    private double latitude;

    @DecimalMin(value = "-180.0", inclusive = true, message = "Longitud mínima -180")
    @DecimalMax(value = "180.0", inclusive = true, message = "Longitud máxima 180")
    private double longitude;

    public City(String name, String country, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.airports = new ArrayList<>();
    }
}