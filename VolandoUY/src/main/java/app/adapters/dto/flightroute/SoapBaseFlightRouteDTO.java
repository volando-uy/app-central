package app.adapters.dto.flightroute;


import domain.models.enums.EnumEstatusRuta;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoapBaseFlightRouteDTO {
    private String name;
    private String description;
    private String createdAt;
    private Double priceTouristClass;
    private Double priceBusinessClass;
    private Double priceExtraUnitBaggage;
    private EnumEstatusRuta status;
    private String image;
    private String videoURL;
    private Double visitCount;

    public SoapBaseFlightRouteDTO(String name, String description, String createdAt, Double priceTouristClass, Double priceBusinessClass, Double priceExtraUnitBaggage, EnumEstatusRuta status, String image, String videoURL) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.priceTouristClass = priceTouristClass;
        this.priceBusinessClass = priceBusinessClass;
        this.priceExtraUnitBaggage = priceExtraUnitBaggage;
        this.status = status;
        this.image = image;
        this.videoURL = videoURL;
        this.visitCount = 0.0;
    }
}
