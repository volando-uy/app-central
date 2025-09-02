package domain.models.bookflight;

import domain.models.buypackage.BuyPackage;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
import domain.models.seat.Seat;
import domain.models.user.Customer;
import domain.models.bookflightticket.BookFlightTicket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    private LocalDateTime created_at;

    // Dueño de la relación
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id")
    private Seat seat;


    //Tiene N equpajes
    @OneToMany(fetch = FetchType.EAGER)
    private List<ExtraLuggage> extraLuggages=null;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BasicLuggage> basicLuggages=null;

    //Comprapaquete
    @ManyToOne(fetch = FetchType.EAGER)
    private BuyPackage buyPackage;
    //Cliente
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    //tiene  muchos pasajes
    @OneToMany(fetch = FetchType.EAGER)
    private List<BookFlightTicket> bookFlightTicket=null;


}
