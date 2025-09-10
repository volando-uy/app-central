package controllers.ticket;

import domain.dtos.ticket.TicketDTO;

public interface ITicketController {
    TicketDTO getTicketDetailsById(Long ticketId);
    TicketDTO getTicketSimpleDetailsById(Long ticketId);
}
