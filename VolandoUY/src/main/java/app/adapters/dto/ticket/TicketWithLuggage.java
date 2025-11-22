package app.adapters.dto.ticket;

import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@XmlRootElement
@Data
public class TicketWithLuggage {
    private BaseTicketDTO ticket;
    private List<LuggageDTO> luggages;

}
