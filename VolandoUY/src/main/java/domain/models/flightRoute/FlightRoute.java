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
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRoute {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories = new ArrayList<>();

    // RELACIÓN BIDIRECCIONAL: vuelo -> ruta
    @OneToMany(
            mappedBy = "flightRoute",
//            cascade = CascadeType.MERGE,   // o sin cascada
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Flight> flights = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    private Airline airline;

    @ManyToOne(fetch = FetchType.EAGER)
    private City originCity;

    @ManyToOne(fetch = FetchType.EAGER)
    private City destinationCity;

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
    }

    @Override
    public String toString() {
        return "FlightRoute{" +
                "categories=" + categories.stream().map(Category::getName).toList() +
                ", flights=" + flights.stream().map(Flight::getName).toList() +
                ", airline=" + (airline != null ? airline.getNickname() : "null") +
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
