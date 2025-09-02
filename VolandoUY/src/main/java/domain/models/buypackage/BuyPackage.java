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

    private LocalDateTime createdAt;

    private double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    //paquete rrua vuelo

    @ManyToOne(fetch = FetchType.EAGER)
    private FlightRoutePackage flightRoutePackage;

    //Lista de reservas
    @OneToMany(fetch = FetchType.EAGER)
    private List<BookFlight> bookFlights;
}
