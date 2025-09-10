package domain.models.seat;

import domain.models.bookflight.BookFlight;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flight.Flight;
import domain.models.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private boolean isAvailable;

    private double price;

    @Enumerated(EnumType.STRING)
    private EnumTipoAsiento type;

    @ManyToOne()
    private Flight flight;

    // Un asiento va a estar relacionado a un ticket
    @OneToOne()
    private Ticket ticket;

    public Seat(String number, boolean isAvailable, double price, EnumTipoAsiento type, Flight flight) {
        this.number = number;
        this.isAvailable = isAvailable;
        this.price = price;
        this.type = type;
        this.flight = flight;
    }
}
