package domain.dtos.flightRoute;

import domain.dtos.category.CategoryDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.models.category.Category;
import domain.models.city.City;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlightRouteDTO extends BaseFlightRouteDTO {
    private String originCityName;
    private String destinationCityName;
    private String airlineNickname;

    private List<String> categories;
    private List<String> flightsNames;
    private List<String> inPackagesNames;

    public FlightRouteDTO(String name, String description, LocalDate createdAt, Double priceTouristClass, Double priceBusinessClass, Double priceExtraUnitBaggage) {
        super(name, description, createdAt, priceTouristClass, priceBusinessClass, priceExtraUnitBaggage);
        this.originCityName = null;
        this.destinationCityName = null;
        this.airlineNickname = null;
        this.categories = null;
        this.flightsNames = null;
        this.inPackagesNames = null;
    }
}
