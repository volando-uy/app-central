package infra.repository.ticket;

import domain.models.ticket.Ticket;
import infra.repository.BaseRepository;

public class AbstractTicketRepository extends BaseRepository<Ticket> {
    public AbstractTicketRepository() {
        super(Ticket.class);
    }
}
