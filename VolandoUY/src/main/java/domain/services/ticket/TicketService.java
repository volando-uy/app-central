package domain.services.ticket;

import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.ticket.Ticket;
import infra.repository.ticket.ITicketRepository;
import lombok.Setter;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;

public class TicketService implements ITicketService {

    @Setter
    private ITicketRepository ticketRepository;

    private final CustomModelMapper customModelMapper;

    // ✅ Inyectamos el mapper por constructor
    public TicketService(CustomModelMapper customModelMapper) {
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

        return full
                ? customModelMapper.mapFullTicket(ticket)
                : customModelMapper.map(ticket, TicketDTO.class);
    }
}
