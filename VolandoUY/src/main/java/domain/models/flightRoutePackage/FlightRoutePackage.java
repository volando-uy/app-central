package domain.models.flightRoutePackage;

import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightRoutePackage {

    @ManyToMany
    private List<FlightRoute> flightRoutes;

    @Id
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

    // 🔹 Método helper para obtener la fecha de expiración
    public LocalDateTime getExpirationDate() {
        return this.creationDate
                .plusDays(this.validityPeriodDays)
                .atStartOfDay();
    }

    // 🔹 Método helper para verificar si ya expiró
    public boolean isExpired() {
        return getExpirationDate().isBefore(LocalDateTime.now());
    }
}