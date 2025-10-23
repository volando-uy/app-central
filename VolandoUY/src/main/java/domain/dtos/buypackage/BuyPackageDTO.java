package domain.dtos.buypackage;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BuyPackageDTO extends BaseBuyPackageDTO{
    private String customerNickname;
    private String flightRoutePackageName;
    private List<Long> bookFlightsIds;

    public BuyPackageDTO(Long id, LocalDateTime createdAt, Double totalPrice) {
        super(id, createdAt, totalPrice);
        this.customerNickname = null;
        this.flightRoutePackageName = null;
        this.bookFlightsIds = null;
    }
}
