package domain.dtos.bookFlight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFlightDTO {
    private Long id;
    private Double totalPrice;
    private LocalDateTime created_at;

    private String customerNickname;
    private List<Long> ticketIds;

}
