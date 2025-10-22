package domain.dtos.flightroutepackage;

import domain.models.enums.EnumTipoAsiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseFlightRoutePackageDTO {
    private String name;
    private String description;
    private int validityPeriodDays;
    private double discount;
    private LocalDate creationDate;
    private EnumTipoAsiento seatType;
    private Double totalPrice;
}
