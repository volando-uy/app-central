package domain.dtos.flightRoutePackage;

import domain.models.enums.EnumTipoAsiento;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlightRoutePackageDTO extends BaseFlightRoutePackageDTO {
    private List<String> flightRouteNames;

    private List<Long> buyPackages;

    public FlightRoutePackageDTO(String name, String description, int validityPeriodDays, double discount, LocalDate creationDate, EnumTipoAsiento seatType, Double totalPrice) {
        super(name, description, validityPeriodDays, discount, creationDate, seatType, totalPrice);
        this.flightRouteNames = null;
    }
}