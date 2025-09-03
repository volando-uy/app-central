package domain.models.luggage;

import domain.models.bookflight.BookFlight;
import domain.models.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ExtraLuggage extends Luggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EnumCategoria category;

    //Estan asociados a 1 ticket
    @ManyToOne(fetch = FetchType.EAGER)
    private Ticket ticket;
}
