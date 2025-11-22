package app.adapters.dto.ticket;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@XmlRootElement
@Data
public class TicketLuggageArray {
    private List<TicketWithLuggage> items;

}