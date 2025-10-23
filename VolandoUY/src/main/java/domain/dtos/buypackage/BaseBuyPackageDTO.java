package domain.dtos.buypackage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseBuyPackageDTO {
    private Long id;
    private LocalDateTime createdAt;
    private Double totalPrice;
}
