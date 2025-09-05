package domain.dtos.luggage;

import domain.models.bookflight.BookFlight;
import domain.models.luggage.EnumEquipajeBasico;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BasicLuggageDTO extends BaseBasicLuggageDTO {
    private Long ticketId;
}
