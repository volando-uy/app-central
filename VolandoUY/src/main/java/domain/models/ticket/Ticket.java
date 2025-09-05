package domain.models.ticket;

import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
import domain.models.luggage.Luggage;
import domain.models.seat.Seat;
import domain.models.bookflight.BookFlight;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String numDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_flight_id")
    private BookFlight bookFlight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasicLuggage> basicLuggages = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtraLuggage> extraLuggages = new ArrayList<>();

    public Ticket(String name, String surname, String numDoc) {
        this.name = name;
        this.surname = surname;
        this.numDoc = numDoc;
    }

    /** Helper que no rompe si las listas están en null y mantiene bidireccionalidad. */
    public void addLuggage(Luggage luggage) {
        if (luggage == null) return;

        if (luggage instanceof BasicLuggage b) {
            if (basicLuggages == null) basicLuggages = new ArrayList<>();
            b.setTicket(this);
            basicLuggages.add(b);
            return;
        }
        if (luggage instanceof ExtraLuggage e) {
            if (extraLuggages == null) extraLuggages = new ArrayList<>();
            e.setTicket(this);
            extraLuggages.add(e);
            return;
        }
        throw new IllegalArgumentException("Tipo de equipaje no soportado: " + luggage.getClass());
    }

    /** Helpers opcionales para evitar manipular colecciones desde afuera. */
    public void addBasicLuggage(BasicLuggage b) { addLuggage(b); }
    public void addExtraLuggage(ExtraLuggage e) { addLuggage(e); }

    /** Defensa extra si JPA/mapper tocó el objeto y dejó null las listas. */
    @PostLoad
    private void ensureLists() {
        if (basicLuggages == null) basicLuggages = new ArrayList<>();
        if (extraLuggages == null) extraLuggages = new ArrayList<>();
    }
}
