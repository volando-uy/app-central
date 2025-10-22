package infra.repository.ticket;

import domain.models.ticket.Ticket;
import infra.repository.IBaseRepository;


public interface ITicketRepository extends IBaseRepository<Ticket> {
    Ticket getTicketById(Long ticketId);

    Ticket getFullTicketById(Long ticketId);
}
