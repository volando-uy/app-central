package app.adapters.soap.booking;

import app.adapters.soap.BaseSoapAdapter;
import controllers.booking.IBookingController;
import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;
import java.util.Map;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BookingSoapAdapter extends BaseSoapAdapter implements IBookingController {

    private final IBookingController controller;

    public BookingSoapAdapter(IBookingController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "bookingService";
    }

    @Override
    @WebMethod
    public BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO, List<LuggageDTO>> tickets, String userNickname, String flightName) {
        return controller.createBooking(bookingDTO, tickets, userNickname, flightName);
    }

    @Override
    @WebMethod
    public List<BookFlightDTO> getAllBookFlightsDetails() {
        return controller.getAllBookFlightsDetails();
    }

    @Override
    @WebMethod
    public List<BaseBookFlightDTO> getAllBookFlightsSimpleDetails() {
        return controller.getAllBookFlightsSimpleDetails();
    }

    @Override
    @WebMethod
    public List<BookFlightDTO> getBookFlightsDetailsByCustomerNickname(String nickname) {
        return controller.getBookFlightsDetailsByCustomerNickname(nickname);
    }

    @Override
    @WebMethod
    public List<BaseBookFlightDTO> getBookFlightsSimpleDetailsByCustomerNickname(String nickname) {
        return controller.getBookFlightsSimpleDetailsByCustomerNickname(nickname);
    }

    @Override
    @WebMethod
    public List<BookFlightDTO> getBookFlightsDetailsByFlightName(String flightName) {
        return controller.getBookFlightsDetailsByFlightName(flightName);
    }

    @Override
    @WebMethod
    public List<BaseBookFlightDTO> getBookFlightsSimpleDetailsByFlightName(String nickname) {
        return controller.getBookFlightsSimpleDetailsByFlightName(nickname);
    }

    @Override
    @WebMethod
    public BookFlightDTO getBookFlightDetailsById(Long id) {
        return controller.getBookFlightDetailsById(id);
    }
}
