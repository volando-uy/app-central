package controllers.ticket;

import domain.dtos.ticket.TicketDTO;
import domain.services.ticket.ITicketService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TicketController implements ITicketController {
    private ITicketService ticketService;

    @Override
    public TicketDTO getTicketDetailsById(Long ticketId) {
        return ticketService.getTicketDetailsById(ticketId, true);
    }

    @Override
    public TicketDTO getTicketSimpleById(Long ticketId) {
        return ticketService.getTicketDetailsById(ticketId, false);
    }
}
