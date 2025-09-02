package domain.models.bookflightticket;

import domain.models.bookflight.BookFlight;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFlightTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    private String numDoc;

    //se asocia a 1 reserva
    @ManyToOne(fetch = FetchType.EAGER)
    private BookFlight bookFlight;
}
