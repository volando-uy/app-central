package app.adapters.dto.ticket;

import domain.dtos.luggage.BaseBasicLuggageDTO;
import domain.dtos.luggage.BaseExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@XmlRootElement
@XmlSeeAlso({BaseBasicLuggageDTO.class, BaseExtraLuggageDTO.class})
public class TicketWithLuggage {
    private BaseTicketDTO ticket;
    private List<LuggageDTO> luggages;

    @XmlElements({
            @XmlElement(name = "basicLuggage", type = BaseBasicLuggageDTO.class),
            @XmlElement(name = "extraLuggage", type = BaseExtraLuggageDTO.class)
    })
    public List<LuggageDTO> getLuggages() {
        return luggages;
    }

    public void setLuggages(List<LuggageDTO> luggages) {
        this.luggages = luggages;
    }

    public BaseTicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(BaseTicketDTO ticket) {
        this.ticket = ticket;
    }
}
