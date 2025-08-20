package domain.dtos.packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
    private String name;
    private String description;
    private int validityPeriodDays;
    private double discount;
    private LocalDate creationDate;
}