package infra.repository.ticket;

import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import infra.repository.IBaseRepository;

import java.util.List;

public interface ITicketRepository extends IBaseRepository<Ticket> {
    Ticket getTicketById(Long ticketId);

    Ticket getFullTicketById(Long ticketId);
}
