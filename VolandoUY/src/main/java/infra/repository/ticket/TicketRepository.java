package infra.repository.ticket;

import app.DBConnection;
import domain.models.flight.Flight;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TicketRepository extends AbstractTicketRepository implements ITicketRepository {
    public TicketRepository() {
        super();
    }

    @Override
    public Ticket getTicketById(Long ticketId) {
        try (EntityManager em = DBConnection.getEntityManager()){
            return em.find(Ticket.class, ticketId);
        }
    }
}
