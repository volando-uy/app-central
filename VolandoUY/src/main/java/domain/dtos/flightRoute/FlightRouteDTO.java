package domain.dtos.flightRoute;

import domain.dtos.category.CategoryDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.city.CityDTO;
import domain.models.category.Category;
import domain.models.city.City;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRouteDTO {
    private String name;
    private String description;
    private LocalDate createdAt;
    private Double priceTouristClass;
    private Double priceBusinessClass;
    private Double priceExtraUnitBaggage;
    private String originCityName;
    private String destinationCityName;
    private String airlineNickname;
    private List<String> categories;
    private List<String> flightsNames;
}
