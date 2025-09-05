package domain.dtos.ticket;

import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.seat.SeatDTO;
import domain.models.bookflight.BookFlight;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
import domain.models.seat.Seat;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TicketDTO extends BaseTicketDTO {

    private List<ExtraLuggageDTO> extraLuggages=null;
    private List<BasicLuggageDTO> basicLuggages=null;
    private String seatNumber;
    private Long bookFlightId;

    public TicketDTO(Long id, String name,String surname,String numdoc) {
        super(id, name,surname,numdoc);
        this.extraLuggages=null;
        this.basicLuggages=null;
        this.seatNumber=null;
        this.bookFlightId=null;
    }
}
