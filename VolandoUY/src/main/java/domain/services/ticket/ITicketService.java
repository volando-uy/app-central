package domain.services.ticket;

import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.ticket.Ticket;
import infra.repository.ticket.ITicketRepository;


public interface ITicketService {
    Ticket createTicketWithoutPersistence(BaseTicketDTO ticket);

    TicketDTO getTicketDetailsById(Long ticketId, boolean b);

}
