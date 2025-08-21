package domain.models.flight;

import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    private FlightRoute flightRoute;

    private Airline airline;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Future(message = "La salida debe ser en el futuro")
    private LocalDateTime departureTime;

    @NotNull
    @Positive(message = "La duración (min) debe ser > 0")
    private Long duration;

    @NotNull
    @PositiveOrZero
    private Integer maxEconomySeats;

    @NotNull
    @PositiveOrZero
    private Integer maxBusinessSeats;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Flight(String name, LocalDateTime departureTime, Long duration, Integer maxEconomySeats, Integer maxBusinessSeats) {
        this.name = name;
        this.departureTime = departureTime;
        this.duration = duration;
        this.maxEconomySeats = maxEconomySeats;
        this.maxBusinessSeats = maxBusinessSeats;
        this.flightRoute = null;
        this.airline = null;
    }
}