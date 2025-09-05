package controllers.ticket;

import domain.dtos.ticket.TicketDTO;

public interface ITicketController {
    TicketDTO getTicketDetailsById(Long ticketId);
    TicketDTO getTicketSimpleById(Long ticketId);
}
