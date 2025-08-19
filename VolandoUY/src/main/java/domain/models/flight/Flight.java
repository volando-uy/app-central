package domain.models.flight;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

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

    @PastOrPresent
    private LocalDateTime createdAt;
}