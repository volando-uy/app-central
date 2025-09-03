package domain.models.buypackage;

import domain.models.bookflight.BookFlight;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BuyPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de compra del paquete
    private LocalDateTime createdAt;

    // Precio de la compra del paquete
    private double totalPrice;

    // Cliente que lo compró
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    //Paquete de ruta de vuelo
    @ManyToOne(fetch = FetchType.EAGER)
    private FlightRoutePackage flightRoutePackage;

    //Lista de reservas
    @OneToMany(fetch = FetchType.EAGER)
    private List<BookFlight> bookFlights;
}
