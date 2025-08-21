package domain.models.flightRoutePackage;

import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;
import java.util.List;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRoutePackage {

    private List<FlightRoute> flightRoutes;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 255)
    private String description;

    @Min(1)
    private int validityPeriodDays;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private double discount;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private EnumTipoAsiento seatType;
}