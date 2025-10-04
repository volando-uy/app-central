package domain.dtos.flightRoute;

import domain.models.enums.EnumEstatusRuta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseFlightRouteDTO {
    private String name;
    private String description;
    private LocalDate createdAt;
    private Double priceTouristClass;
    private Double priceBusinessClass;
    private Double priceExtraUnitBaggage;
    private EnumEstatusRuta status;
    private String image;
}
