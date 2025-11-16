package domain.dtos.seat;

import domain.models.enums.EnumTipoAsiento;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseSeatDTO")
@XmlRootElement
public class BaseSeatDTO {
    private Long id;
    private String number;
    private boolean isAvailable;
    private double price;
    private EnumTipoAsiento type;
}
