package domain.models.flight;

import domain.models.airport.Airport;
import domain.models.flightroute.FlightRoute;
import domain.models.seat.Seat;
import domain.models.user.Airline;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.constants.ErrorMessages;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Future(message = ErrorMessages.ERR_FLIGHT_DEPARTURE_FUTURE)
    private LocalDateTime departureTime;


    @Enumerated(EnumType.STRING)
    private EnumStatusVuelo status;

    @NotNull
    @Positive(message = ErrorMessages.ERR_FLIGHT_DURATION_POSITIVE)
    // In minutes
    private Long duration;


    @NotNull
    @PositiveOrZero
    @Max(value = 500, message = ErrorMessages.ERR_FLIGHT_MAX_ECONOMY_SEATS)
    private Integer maxEconomySeats;

    @NotNull
    @Max(value = 500, message = ErrorMessages.ERR_FLIGHT_MAX_BUSINESS_SEATS)
    @PositiveOrZero
    private Integer maxBusinessSeats;


    @ManyToOne
    @JoinColumn(name = "flightroute_name", referencedColumnName = "name")
    private FlightRoute flightRoute;

    @ManyToOne
    private Airline airline;


    //Muchos Aereopuetos tienen 1 vuelo
    @ManyToOne()
    private Airport originAirport;

    @ManyToOne()
    private Airport destinationAirport;

    //1 vuelo tiene muchos asientos
    @OneToMany(mappedBy = "flight")
    private List<Seat> seats;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    private String image;

    public Flight(String name, LocalDateTime departureTime, Long duration, Integer maxEconomySeats, Integer maxBusinessSeats, String image) {
        this.name = name;
        this.departureTime = departureTime;
        this.duration = duration;
        this.maxEconomySeats = maxEconomySeats;
        this.maxBusinessSeats = maxBusinessSeats;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + name + '\'' +
                ", departureTime=" + departureTime +
                ", status=" + status +
                ", duration=" + duration +
                ", maxEconomySeats=" + maxEconomySeats +
                ", maxBusinessSeats=" + maxBusinessSeats +
                ", createdAt=" + createdAt +
                ", image='" + image +
                '}';
    }

    public double getTotalDurationInHours() {
        return (double) duration / 60;
    }
}
