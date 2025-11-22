package app.adapters.soap.booking;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import app.adapters.dto.ticket.TicketLuggageArray;
import app.adapters.dto.ticket.TicketWithLuggage;
import app.adapters.mappers.BookFlightSoapMapper;
import app.adapters.mappers.TicketLuggageMapper;
import app.adapters.soap.BaseSoapAdapter;
import controllers.booking.IBookingController;
import controllers.booking.IBookingSoapController;
import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BookingSoapAdapter extends BaseSoapAdapter implements IBookingSoapController {

    private final IBookingController controller;

    public BookingSoapAdapter(IBookingController controller) {
        this.controller = controller;
    }

    protected String getServiceName() {
        return "bookingService";
    }

    @Override
    @WebMethod
    public SoapBaseBookFlightDTO createBooking(SoapBaseBookFlightDTO soapBookingDTO,
                                               TicketLuggageArray ticketLuggages,
                                               String userNickname,
                                               String flightName) {

        // Convert SOAP DTO to domain DTO
        BaseBookFlightDTO bookingDTO = BookFlightSoapMapper.fromSoap(soapBookingDTO);

        // Convert SOAP luggage structure to map
        Map<BaseTicketDTO, List<LuggageDTO>> ticketMap = TicketLuggageMapper.toMap(ticketLuggages);

        // Call controller
        BaseBookFlightDTO result = controller.createBooking(bookingDTO, ticketMap, userNickname, flightName);

        // Convert domain DTO back to SOAP DTO
        return BookFlightSoapMapper.toSoap(result);
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
