package domain.models.flightRoute;

import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flight.Flight;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRoute {

    @NotNull
    private List<Category> category; // categoriaVuelo

    @NotNull
    private List<Flight> flights; // vuelos asociados a la ruta

    @NotNull
    private City originCity;

    @NotNull
    private City destinationCity;

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



    public void addCategory(Category category) {
        if (this.category == null) {
            this.category = new ArrayList<>();
        }
        this.category.add(category);
    }
    public void addFlight(Flight flight) {
        if (this.flights == null) {
            this.flights = new ArrayList<>();
        }
        this.flights.add(flight);
    }
}
