package domain.dtos.luggage;

import domain.models.bookflight.BookFlight;
import domain.models.luggage.EnumEquipajeBasico;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

public class BasicLuggageDTO extends LuggageDTO {
    private Long id;
    private EnumEquipajeBasico category;

    private Long bookFlightId;
}
