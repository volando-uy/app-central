package domain.services.ticket;

import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.ticket.Ticket;
import factory.ControllerFactory;
import infra.repository.ticket.TicketRepository;
import shared.utils.CustomModelMapper;

import java.util.ArrayList;

public class TicketService implements ITicketService {
    private TicketRepository ticketRepository;

    private CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public TicketService() {
        this.ticketRepository = new TicketRepository();
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
            throw new IllegalArgumentException("Ticket not found");
        }

        return full ? customModelMapper.mapFullTicket(ticket) : customModelMapper.map(ticket, TicketDTO.class);
    }

}
