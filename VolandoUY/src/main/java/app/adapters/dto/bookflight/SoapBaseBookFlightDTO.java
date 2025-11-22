package app.adapters.dto.bookflight;


import app.adapters.dto.localdatetime.LocalDateTimeWithValue;
import domain.models.enums.EnumTipoAsiento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SoapBaseBookFlightDTO {

    private Long id;
    private LocalDateTimeWithValue createdAt;
    private Double totalPrice;
    private EnumTipoAsiento seatType;
}