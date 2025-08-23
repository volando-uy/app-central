package domain.models.flight;

import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @ManyToOne
    private FlightRoute flightRoute;

    @ManyToOne
    private Airline airline;

    @Id
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

    @Override
    public String toString() {
        return "Flight{" +
                "flightRoute=" + (flightRoute != null ? flightRoute.getName() : "null") +
                ", airline=" + (airline != null ? airline.getNickname() : "null") +
                ", name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", duration=" + duration +
                ", maxEconomySeats=" + maxEconomySeats +
                ", maxBusinessSeats=" + maxBusinessSeats +
                ", createdAt=" + createdAt +
                '}';
    }

}