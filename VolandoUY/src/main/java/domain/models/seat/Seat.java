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
    @ManyToOne()
    private Ticket ticket;
}
