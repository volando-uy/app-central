package infra.repository.ticket;

import domain.models.seat.Seat;
import domain.models.ticket.Ticket;

import java.util.List;

public interface ITicketRepository {
    Ticket getTicketById(Long ticketId);

    Ticket getFullTicketById(Long ticketId);
}
