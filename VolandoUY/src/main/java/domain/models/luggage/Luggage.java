package domain.models.luggage;

import domain.models.ticket.Ticket;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Luggage {
    private double weight;

    @ManyToOne()
    private Ticket ticket;
}
