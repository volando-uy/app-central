package domain.services.ticket;

import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.ticket.Ticket;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.ticket.ITicketRepository;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;


public class TicketService implements ITicketService {
    private ITicketRepository ticketRepository;

    private CustomModelMapper customModelMapper;

    public TicketService() {
        this.customModelMapper = ControllerFactory.getCustomModelMapper();
        this.ticketRepository = RepositoryFactory.getTicketRepository();
    }

    TicketService(CustomModelMapper customModelMapper, ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
        this.customModelMapper = customModelMapper;
    }

    @Override
    public Ticket createTicketWithoutPersistence(BaseTicketDTO ticket) {
        Ticket newTicket = customModelMapper.map(ticket, Ticket.class);
        newTicket.setSeat(null);
        newTicket.setBookFlight(null);
        newTicket.setBasicLuggages(null);
        newTicket.setExtraLuggages(null);
        return newTicket;
    }

    @Override
    public TicketDTO getTicketDetailsById(Long ticketId, boolean full) {

        Ticket ticket = full ? ticketRepository.getFullTicketById(ticketId) : ticketRepository.findByKey(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException(ErrorMessages.ERROR_TICKET_NOT_FOUND);
        }

        return full ? customModelMapper.mapFullTicket(ticket) : customModelMapper.map(ticket, TicketDTO.class);
    }


}
