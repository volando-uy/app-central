package domain.dtos.luggage;

import domain.models.luggage.EnumEquipajeExtra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ExtraLuggageDTO extends BaseExtraLuggageDTO {
    private Long ticketId;

    public ExtraLuggageDTO(Double weight, Long id, EnumEquipajeExtra category) {
        super(weight, id ,category);
        this.ticketId = null;
    }
}
