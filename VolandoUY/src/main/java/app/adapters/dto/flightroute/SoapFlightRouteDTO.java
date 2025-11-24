package app.adapters.dto.flightroute;

import domain.models.enums.EnumEstatusRuta;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoapFlightRouteDTO extends SoapBaseFlightRouteDTO {
    private String originAeroCode;
    private String destinationAeroCode;
    private String airlineNickname;

    private List<String> categoriesNames;
    private List<String> flightsNames;
    private List<String> inPackagesNames;

    public SoapFlightRouteDTO(String name, String description, String createdAt, Double priceTouristClass, Double priceBusinessClass, Double priceExtraUnitBaggage, EnumEstatusRuta status, String image, String videoURL, Double visitCount) {
        super(name, description, createdAt, priceTouristClass, priceBusinessClass, priceExtraUnitBaggage, status, image, videoURL, visitCount);
        this.originAeroCode = null;
        this.destinationAeroCode = null;
        this.airlineNickname = null;
        this.categoriesNames = null;
        this.flightsNames = null;
        this.inPackagesNames = null;
    }
}
