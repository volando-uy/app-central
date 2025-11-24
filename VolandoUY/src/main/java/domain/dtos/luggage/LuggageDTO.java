package domain.dtos.luggage;

import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlSeeAlso({BaseBasicLuggageDTO.class, BaseExtraLuggageDTO.class})
public abstract class LuggageDTO {
    protected Double weight;
}
