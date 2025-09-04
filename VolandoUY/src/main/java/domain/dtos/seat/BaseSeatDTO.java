package domain.dtos.seat;

import domain.models.enums.EnumTipoAsiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseSeatDTO {
    private Long id;
    private String number;
    private boolean isAvailable;
    private double price;
    private EnumTipoAsiento type;
}
