package domain.dtos.seat;

import domain.models.bookflight.BookFlight;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flight.Flight;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SeatDTO extends BaseSeatDTO {
    private String flightName;
    private Long ticketId;
}
