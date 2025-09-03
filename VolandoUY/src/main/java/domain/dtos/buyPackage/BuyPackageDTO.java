package domain.dtos.buyPackage;

import domain.models.bookflight.BookFlight;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyPackageDTO {
    private Long id;
    private LocalDateTime createdAt;
    private double totalPrice;

    private String customerNickname;
    private String flightRoutePackageName;
    private List<Long> bookFlightsIds;
}
