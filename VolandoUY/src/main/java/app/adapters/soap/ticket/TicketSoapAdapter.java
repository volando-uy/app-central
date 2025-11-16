package app.adapters.soap.ticket;

import app.adapters.soap.BaseSoapAdapter;
import controllers.seat.ISeatController;
import controllers.ticket.ITicketController;
import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.dtos.ticket.TicketDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TicketSoapAdapter extends BaseSoapAdapter implements ITicketController {

    private final ITicketController controller;

    public TicketSoapAdapter(ITicketController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "ticketService";
    }

    @Override
    @WebMethod
    public TicketDTO getTicketDetailsById(Long ticketId) {
        return controller.getTicketDetailsById(ticketId);
    }

    @Override
    @WebMethod
    public TicketDTO getTicketSimpleDetailsById(Long ticketId) {
        return controller.getTicketSimpleDetailsById(ticketId);
    }
}
