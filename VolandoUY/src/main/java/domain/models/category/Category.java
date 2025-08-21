package domain.models.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.utils.ValidatorUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

}
