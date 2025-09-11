package domain.dtos.bookFlight;

import domain.models.enums.EnumTipoAsiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseBookFlightDTO {
    private Long id;
    private LocalDateTime createdAt;
    private Double totalPrice;
    private EnumTipoAsiento seatType;
}
