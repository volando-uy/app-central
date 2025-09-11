package domain.dtos.luggage;

import domain.models.bookflight.BookFlight;
import domain.models.luggage.EnumEquipajeBasico;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BasicLuggageDTO extends BaseBasicLuggageDTO {
    private Long ticketId;

    public BasicLuggageDTO(Double weight, Long id, EnumEquipajeBasico type, Long ticketId) {
        super(weight, id, type);
        this.ticketId = ticketId;
    }
}
