package domain.dtos.luggage;

import domain.models.luggage.EnumCategoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class BaseExtraLuggageDTO extends LuggageDTO {
    private Long id;
    private EnumCategoria category;

}
