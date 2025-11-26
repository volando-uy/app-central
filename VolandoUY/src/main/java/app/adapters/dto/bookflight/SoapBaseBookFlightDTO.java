package app.adapters.dto.bookflight;


import domain.models.enums.EnumTipoAsiento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoapBaseBookFlightDTO {
    private Long id;
    private String createdAt;
    private Double totalPrice;
    private EnumTipoAsiento seatType;
    private boolean isBooked;
}