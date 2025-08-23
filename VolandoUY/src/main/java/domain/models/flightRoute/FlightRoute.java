package domain.models.flightRoute;

import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightRoute {

    @ManyToMany
    private List<Category> categories; // categoriaVuelo

    @OneToMany
    private List<Flight> flights; // vuelos asociados a la ruta

    @ManyToOne
    private Airline airline; // Aerolinea duenia

    @ManyToOne
    private City originCity; // Ciudad de origen

    @ManyToOne
    private City destinationCity; // Ciudad de destino

    @Id
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String description;

    @NotNull
    private LocalDate createdAt;

    @NotNull
    @PositiveOrZero()
    private Double priceTouristClass;

    @PositiveOrZero()
    private Double priceBusinessClass;

    @PositiveOrZero()
    private Double priceExtraUnitBaggage;

    public FlightRoute(String name, String description, LocalDate createdAt,
                       Double priceTouristClass, Double priceBusinessClass,
                       Double priceExtraUnitBaggage) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.priceTouristClass = priceTouristClass;
        this.priceBusinessClass = priceBusinessClass;
        this.priceExtraUnitBaggage = priceExtraUnitBaggage;
        this.categories = new ArrayList<>();
        this.flights = new ArrayList<>();
        this.airline = null;
        this.originCity = null;
        this.destinationCity = null;
    }

    @Override
    public String toString() {
        return "FlightRoute{" +
                "categories=" + categories.stream().map(Category::getName).toList() +
                ", flights=" + flights.stream().map(Flight::getName).toList() +
                ", airline=" + airline.getNickname() +
                ", originCity=" + originCity +
                ", destinationCity=" + destinationCity +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", priceTouristClass=" + priceTouristClass +
                ", priceBusinessClass=" + priceBusinessClass +
                ", priceExtraUnitBaggage=" + priceExtraUnitBaggage +
                '}';
    }
}
