package domain.dtos.seat;

import domain.models.enums.EnumTipoAsiento;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SeatDTO extends BaseSeatDTO {
    private String flightName;
    private Long ticketId;

    public SeatDTO(Long id, String number, boolean isAvailable, double price, EnumTipoAsiento type) {
        super(id, number, isAvailable, price, type);
        this.flightName = null;
        this.ticketId = null;
    }
}
