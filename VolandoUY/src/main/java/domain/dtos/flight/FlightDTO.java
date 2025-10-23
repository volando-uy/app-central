package domain.dtos.flight;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FlightDTO extends BaseFlightDTO {
    private String airlineNickname;
    private String flightRouteName;

    public FlightDTO(String name, LocalDateTime departureTime, Long duration, Integer maxEconomySeats, Integer maxBusinessSeats, LocalDateTime createdAt, String image) {
        super(name, departureTime, duration, maxEconomySeats, maxBusinessSeats, createdAt, image);
        this.airlineNickname = null;
        this.flightRouteName = null;
    }
}
