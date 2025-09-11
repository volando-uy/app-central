package domain.dtos.bookFlight;

import domain.models.enums.EnumTipoAsiento;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookFlightDTO extends BaseBookFlightDTO {
    private String customerNickname;
    private List<Long> ticketIds;

    public BookFlightDTO(Long id, LocalDateTime createdAt, Double totalPrice, EnumTipoAsiento seatType) {
        super(id, createdAt, totalPrice, seatType);
        this.customerNickname = null;
        this.ticketIds = null;
    }
}
