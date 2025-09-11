package domain.models.flightRoute;

import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flight.Flight;
import domain.models.flightRoutePackage.FlightRoutePackage;
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


    @ManyToOne()
    private City originCity;

    @ManyToOne()
    private City destinationCity;

    @ManyToMany
    @JoinTable(
            name = "flightroute_category",
            joinColumns = @JoinColumn(name = "flightroute_name"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    // RELACIÓN BIDIRECCIONAL: vuelo -> ruta
    @OneToMany(mappedBy = "flightRoute")
    private List<Flight> flights = new ArrayList<>();

    @ManyToMany(mappedBy = "flightRoutes")
    private List<FlightRoutePackage> inPackages = new ArrayList<>();

    @ManyToOne()
    private Airline airline;




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
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", priceTouristClass=" + priceTouristClass +
                ", priceBusinessClass=" + priceBusinessClass +
                ", priceExtraUnitBaggage=" + priceExtraUnitBaggage +
                '}';
    }
}
