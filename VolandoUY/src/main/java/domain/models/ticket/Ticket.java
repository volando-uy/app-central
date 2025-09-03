package domain.models.ticket;

import domain.models.bookflight.BookFlight;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
import domain.models.seat.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String numDoc;

    //Tiene N equpajes
    @OneToMany(fetch = FetchType.EAGER)
    private List<ExtraLuggage> extraLuggages=null;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BasicLuggage> basicLuggages=null;

    // Tiene 1 asiento
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    //se asocia a 1 reserva
    @ManyToOne(fetch = FetchType.EAGER)
    private BookFlight bookFlight;
}
