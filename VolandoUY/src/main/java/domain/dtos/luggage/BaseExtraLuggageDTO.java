package domain.dtos.luggage;

import domain.models.luggage.EnumEquipajeExtra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BaseExtraLuggageDTO extends LuggageDTO {
    private Long id;
    private EnumEquipajeExtra category;


    public BaseExtraLuggageDTO(Double weight, Long id, EnumEquipajeExtra category) {
        super(weight);
        this.id = id;
        this.category = category;
    }

}
