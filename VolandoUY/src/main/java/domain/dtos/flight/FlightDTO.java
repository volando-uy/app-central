package domain.dtos.flight;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.flightRoute.FlightRoute;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FlightDTO extends BaseFlightDTO {
    private String airlineNickname;
    private String flightRouteName;

    public FlightDTO(String name, LocalDateTime departureTime, Long duration, Integer maxEconomySeats, Integer maxBusinessSeats, LocalDateTime createdAt) {
        super(name, departureTime, duration, maxEconomySeats, maxBusinessSeats, createdAt);
        this.airlineNickname = null;
        this.flightRouteName = null;
    }
}
