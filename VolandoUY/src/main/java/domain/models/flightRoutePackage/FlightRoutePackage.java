package domain.models.flightRoutePackage;

import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Customer;
import domain.models.user.User;
import jakarta.persistence.*;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<FlightRoute> flightRoutes;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Customer> buyers;

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

    //Tiene muchos compras de paquete
    @OneToMany(fetch = FetchType.EAGER)
    private List<BuyPackage> buyPackages;


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