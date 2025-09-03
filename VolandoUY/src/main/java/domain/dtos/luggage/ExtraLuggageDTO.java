package domain.dtos.luggage;

import domain.models.bookflight.BookFlight;
import domain.models.luggage.EnumCategoria;
import jakarta.persistence.*;

public class ExtraLuggageDTO extends LuggageDTO {
    private Long id;
    private EnumCategoria category;

    private Long bookFlightId;
}
