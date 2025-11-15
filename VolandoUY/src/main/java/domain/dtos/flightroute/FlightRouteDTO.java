package domain.dtos.flightroute;

import domain.models.enums.EnumEstatusRuta;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlightRouteDTO extends BaseFlightRouteDTO {
    private String originAeroCode;
    private String destinationAeroCode;
    private String airlineNickname;

    private List<String> categoriesNames;
    private List<String> flightsNames;
    private List<String> inPackagesNames;

    public FlightRouteDTO(String name, String description, LocalDate createdAt, Double priceTouristClass, Double priceBusinessClass, Double priceExtraUnitBaggage, EnumEstatusRuta status, String image, String videoURL) {
        super(name, description, createdAt, priceTouristClass, priceBusinessClass, priceExtraUnitBaggage, status, image, videoURL);
        this.originAeroCode = null;
        this.destinationAeroCode = null;
        this.airlineNickname = null;
        this.categoriesNames = null;
        this.flightsNames = null;
        this.inPackagesNames = null;
    }
}
