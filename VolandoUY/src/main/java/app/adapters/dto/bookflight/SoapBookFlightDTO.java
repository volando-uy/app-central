package app.adapters.dto.bookflight;

import domain.models.enums.EnumTipoAsiento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class SoapBookFlightDTO extends SoapBaseBookFlightDTO {
    private String customerNickname;
    private List<Long> ticketIds;

    public SoapBookFlightDTO(Long id, String createdAt, Double totalPrice, EnumTipoAsiento seatType) {
        super(id, createdAt, totalPrice, seatType);
        this.customerNickname = null;
        this.ticketIds = null;
    }
}
