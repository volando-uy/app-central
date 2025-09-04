package domain.services.ticket;

import factory.ControllerFactory;
import infra.repository.ticket.TicketRepository;
import shared.utils.CustomModelMapper;

public class TicketService implements ITicketService {
    private TicketRepository ticketRepository;

    private CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public TicketService() {
        this.ticketRepository = new TicketRepository();
    }


}
