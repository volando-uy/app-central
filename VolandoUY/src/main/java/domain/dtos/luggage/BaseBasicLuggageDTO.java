package domain.dtos.luggage;

import domain.models.luggage.EnumEquipajeBasico;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseBasicLuggageDTO extends LuggageDTO {
    private Long id;
    private EnumEquipajeBasico category;
}
