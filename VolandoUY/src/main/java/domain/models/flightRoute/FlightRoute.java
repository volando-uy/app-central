package domain.models.flightRoute;

import domain.models.category.Category;
import domain.models.city.City;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRoute {

    @NotNull
    private List<Category> category; // categoriaVuelo

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
}
