package domain.models.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    // Primary key
    private String name;
    // private Airline airline;
    private LocalDateTime departureTime;

    /**
     * The duration of the flight.
     * Is represented in minutes.
     */
    private Long duration;

    private Integer maxEconomySeats;
    private Integer maxBusinessSeats;

    private LocalDateTime createdAt;


}
