package domain.dtos.ticket;

import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.seat.SeatDTO;
import domain.models.bookflight.BookFlight;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
import domain.models.seat.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Long id;

    private String name;
    private String surname;
    private String numDoc;

    private List<ExtraLuggageDTO> extraLuggages=null;
    private List<BasicLuggageDTO> basicLuggages=null;
    private String seatNumber;
    private Long bookFlightId;
}
