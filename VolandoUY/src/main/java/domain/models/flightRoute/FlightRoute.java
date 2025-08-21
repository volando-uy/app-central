package domain.models.flightRoute;

import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRoute {

    private List<Category> categories; // categoriaVuelo

    private List<Flight> flights; // vuelos asociados a la ruta

    private Airline airline; // Aerolinea duenia

    private City originCity; // Ciudad de origen

    private City destinationCity; // Ciudad de destino

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String description;

    @NotNull
    @FutureOrPresent(message = "La fecha de salida debe ser en el futuro o presente")
    private LocalDateTime createdAt;

    @NotNull
    @PositiveOrZero()
    private Double priceTouristClass;

    @PositiveOrZero()
    private Double priceBusinessClass;

    @PositiveOrZero()
    private Double priceExtraUnitBaggage;

    public FlightRoute(String name, String description, LocalDateTime createdAt,
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
}
