package domain.models.city;

import domain.models.airport.Airport;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City {
    @Id
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String country;

    @DecimalMin(value = "-90.0", message = ErrorMessages.ERROR_MIN_LATITUDE)
    @DecimalMax(value = "90.0", message = ErrorMessages.ERROR_MAX_LATITUDE)
    private double latitude;

    @DecimalMin(value = "-180.0", message = ErrorMessages.ERROR_MIN_LONGITUDE)
    @DecimalMax(value = "180.0", message = ErrorMessages.ERROR_MAX_LONGITUDE)
    private double longitude;


    @Valid // valida cada Airport dentro de la lista
    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private List<Airport> airports = new ArrayList<>();


    public City(String name, String country, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.airports = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "City{" +
                "airports=" + airports.stream().map(Airport::getName).toList() +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}