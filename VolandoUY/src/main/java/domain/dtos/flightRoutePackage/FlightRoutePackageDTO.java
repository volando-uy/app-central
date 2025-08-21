package domain.dtos.flightRoutePackage;

import domain.models.enums.EnumTipoAsiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRoutePackageDTO {
    private String name;
    private String description;
    private int validityPeriodDays;
    private double discount;
    private LocalDate creationDate;
    private EnumTipoAsiento seatType;
}