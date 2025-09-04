package domain.dtos.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseFlightDTO {
    private String name;
    private LocalDateTime departureTime;
    private Long duration;
    private Integer maxEconomySeats;
    private Integer maxBusinessSeats;
    private LocalDateTime createdAt;
}
