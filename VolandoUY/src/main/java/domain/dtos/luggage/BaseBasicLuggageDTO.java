package domain.dtos.luggage;

import domain.models.luggage.EnumEquipajeBasico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BaseBasicLuggageDTO extends LuggageDTO {
    private Long id;
    private EnumEquipajeBasico category;


    public BaseBasicLuggageDTO(Double weight, Long id, EnumEquipajeBasico category) {
        super(weight);
        this.id = id;
        this.category = category;
    }
}
