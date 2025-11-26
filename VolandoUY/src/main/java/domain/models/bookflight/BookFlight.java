package domain.models.bookflight;

import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumTipoAsiento;
import domain.models.user.Customer;
import domain.models.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Precio total de la reserva
    private double totalPrice;

    // Fecha de compra
    private LocalDateTime createdAt;

    @Enumerated(EnumType.ORDINAL)
    private EnumTipoAsiento seatType;

    // Si la reserva viene de un paquete o no
    @ManyToOne
    private BuyPackage buyPackage = null;

    // Cliente que lo reservó
    @ManyToOne
    private Customer customer;

    // Tiene muchos tickets
    @OneToMany(mappedBy = "bookFlight")
    private List<Ticket> tickets = new ArrayList<>();

    private boolean isBooked;

}
