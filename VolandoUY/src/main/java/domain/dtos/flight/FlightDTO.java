package domain.dtos.flight;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.flightRoute.FlightRoute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String name;
    private LocalDateTime departureTime;
    private Long duration;
    private Integer maxEconomySeats;
    private Integer maxBusinessSeats;
    private LocalDateTime createdAt;
    private String airlineNickname;
    private String flightRouteName;
}
