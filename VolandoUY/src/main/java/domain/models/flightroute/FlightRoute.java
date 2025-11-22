package domain.models.flightroute;

import domain.models.airport.Airport;
import domain.models.category.Category;
import domain.models.enums.EnumEstatusRuta;
import domain.models.flight.Flight;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.models.user.Airline;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @Size(min = 2, max = 300)
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

    @NotNull
    private EnumEstatusRuta status = EnumEstatusRuta.SIN_ESTADO;


    @ManyToOne()
    private Airport originAero;

    @ManyToOne()
    private Airport destinationAero;

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

    private String image;

    private String videoURL;

    private Double visitCount = 0.0;


    public FlightRoute(String name, String description, LocalDate createdAt,
                       Double priceTouristClass, Double priceBusinessClass,
                       Double priceExtraUnitBaggage, String image, String videoURL) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.priceTouristClass = priceTouristClass;
        this.priceBusinessClass = priceBusinessClass;
        this.priceExtraUnitBaggage = priceExtraUnitBaggage;
        this.image = image;
        this.videoURL = videoURL;
    }

    @Override
    public String toString() {
        return "FlightRoute{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt + '\'' +
                ", priceTouristClass=" + priceTouristClass + '\'' +
                ", priceBusinessClass=" + priceBusinessClass + '\'' +
                ", priceExtraUnitBaggage=" + priceExtraUnitBaggage + '\'' +
                ", image='" + image + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", visitCount=" + visitCount + '\'' +
                '}';
    }
}
