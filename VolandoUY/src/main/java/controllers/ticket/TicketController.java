package controllers.ticket;

import domain.services.ticket.ITicketService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TicketController implements ITicketController {
    private ITicketService ticketService;
}
