package infra.repository.ticket;

import app.DBConnection;
import domain.dtos.ticket.TicketDTO;
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

    @Override
    public Ticket getFullTicketById(Long ticketId) {
        try(EntityManager em = DBConnection.getEntityManager()){
            Ticket ticket = em.find(Ticket.class, ticketId);
            if(ticket != null){
                if(ticket.getSeat() != null){
                    ticket.getSeat().getNumber();
                }
                if(ticket.getBookFlight() != null){
                    ticket.getBookFlight().getId();
                }
                if(ticket.getBasicLuggages() != null){
                    ticket.getBasicLuggages().size();
                }
                if(ticket.getExtraLuggages() != null){
                    ticket.getExtraLuggages().size();
                }
            }
            return ticket;
        }
    }
}
