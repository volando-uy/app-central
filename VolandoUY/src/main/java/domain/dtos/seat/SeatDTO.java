package domain.dtos.seat;

import domain.models.bookflight.BookFlight;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flight.Flight;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
    private Long id;
    private String number;
    private boolean isAvailable;
    private double price;
    private EnumTipoAsiento type;

    private String flightName;
    private Long ticketId;
}
