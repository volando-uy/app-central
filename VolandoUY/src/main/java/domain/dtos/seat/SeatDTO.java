package domain.dtos.seat;

import domain.models.enums.EnumTipoAsiento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatDTO")
@XmlRootElement
public class SeatDTO extends BaseSeatDTO {
    private String flightName;
    private Long ticketId;

    public SeatDTO(Long id, String number, boolean isAvailable, double price, EnumTipoAsiento type) {
        super(id, number, isAvailable, price, type);
        this.flightName = null;
        this.ticketId = null;
    }
}
