package domain.models.packagePurchase;

import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackagePurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private FlightRoutePackage flightRoutePackage;

    private LocalDateTime purchaseDate = LocalDateTime.now();

    private LocalDateTime expirationDate;

    public PackagePurchase(Customer customer, FlightRoutePackage flightRoutePackage) {
        this.customer = customer;
        this.flightRoutePackage = flightRoutePackage;
        this.purchaseDate = LocalDateTime.now();

        // Expiración según el paquete
        this.expirationDate = flightRoutePackage.getExpirationDate();

        // Validar que no se pueda comprar un paquete vencido
        if (this.expirationDate.isBefore(this.purchaseDate)) {
            throw new IllegalArgumentException("El paquete ya está vencido y no puede ser comprado.");
        }
    }

    public void setPurchaseDate(LocalDate now) {
        this.purchaseDate = now.atStartOfDay();
    }
}
