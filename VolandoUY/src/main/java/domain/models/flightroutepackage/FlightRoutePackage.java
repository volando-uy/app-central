package domain.models.flightroutepackage;

import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightroute.FlightRoute;
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

    @ManyToMany
    @JoinTable(
            name = "flight_route_package_join",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_route_id")
    )
    private List<FlightRoute> flightRoutes;

    //Tiene muchos compras de paquete
    @OneToMany(mappedBy = "flightRoutePackage")
    private List<BuyPackage> buyPackages;

    @Id
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 255)
    private String description;

    @Min(1)
    private int validityPeriodDays;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private double discount;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private EnumTipoAsiento seatType;

    @NotNull
    private Double totalPrice;



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