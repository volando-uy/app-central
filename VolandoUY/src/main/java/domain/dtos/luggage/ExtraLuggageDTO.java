package domain.dtos.luggage;

import domain.models.bookflight.BookFlight;
import domain.models.luggage.EnumCategoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExtraLuggageDTO extends BaseExtraLuggageDTO {

    private Long ticketId;
}
