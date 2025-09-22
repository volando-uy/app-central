package domain.services.ticket;

import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.ticket.Ticket;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.ticket.ITicketRepository;
import infra.repository.ticket.TicketRepository;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;

import java.util.ArrayList;

public class TicketService implements ITicketService {
    private final ITicketRepository ticketRepository;

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public TicketService() {
        this.ticketRepository = RepositoryFactory.getTicketRepository();
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
