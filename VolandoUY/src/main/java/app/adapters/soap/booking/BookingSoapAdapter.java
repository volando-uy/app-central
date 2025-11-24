package app.adapters.soap.booking;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import app.adapters.dto.bookflight.SoapBookFlightDTO;
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
import org.apache.cxf.wsdl11.SOAPBindingUtil;

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
        System.out.println("BookingDTO antes del mapeo a base" + soapBookingDTO.toString());
        BaseBookFlightDTO bookingDTO = BookFlightSoapMapper.toBaseBookFlightDTO(soapBookingDTO);
        System.out.println("BookingDTO despues del mapeo a base" + bookingDTO.toString());
        // Convert SOAP luggage structure to map
        System.out.println("Tickets antes del mapeo" + ticketLuggages.toString());
        Map<BaseTicketDTO, List<LuggageDTO>> ticketMap = TicketLuggageMapper.toMap(ticketLuggages);
        System.out.println("Tickets despues del mapeo" + ticketMap.toString());
        // Call controller
        BaseBookFlightDTO result = controller.createBooking(bookingDTO, ticketMap, userNickname, flightName);
        System.out.println("Booking creado" + result.toString());
        System.out.println("Booking a devolver" + BookFlightSoapMapper.toSoapBaseBookFlightDTO(result).toString());
        // Convert domain DTO back to SOAP DTO
        return BookFlightSoapMapper.toSoapBaseBookFlightDTO(result);
    }
    @Override
    @WebMethod
    public List<SoapBookFlightDTO> getAllBookFlightsDetails() {
        List<BookFlightDTO> bookFlightDTOS= controller.getAllBookFlightsDetails();
        return bookFlightDTOS.stream()
                .map(BookFlightSoapMapper::toSoapBookFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseBookFlightDTO> getAllBookFlightsSimpleDetails() {
        List<BaseBookFlightDTO> baseBookFlightDTOS =  controller.getAllBookFlightsSimpleDetails();
        return baseBookFlightDTOS.stream()
                .map(BookFlightSoapMapper::toSoapBaseBookFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBookFlightDTO> getBookFlightsDetailsByCustomerNickname(String nickname) {

        List<BookFlightDTO> bookFlightDTOS= controller.getBookFlightsDetailsByCustomerNickname(nickname);
        return bookFlightDTOS.stream()
                .map(BookFlightSoapMapper::toSoapBookFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseBookFlightDTO> getBookFlightsSimpleDetailsByCustomerNickname(String nickname) {
        List<BaseBookFlightDTO> baseBookFlightDTOS = controller.getBookFlightsSimpleDetailsByCustomerNickname(nickname);
        return baseBookFlightDTOS.stream()
                .map(BookFlightSoapMapper::toSoapBaseBookFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBookFlightDTO> getBookFlightsDetailsByFlightName(String flightName) {
        List<BookFlightDTO> bookFlightDTOS= controller.getBookFlightsDetailsByFlightName(flightName);
        return bookFlightDTOS.stream()
                .map(BookFlightSoapMapper::toSoapBookFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseBookFlightDTO> getBookFlightsSimpleDetailsByFlightName(String nickname) {
        List<BaseBookFlightDTO> baseBookFlightDTOS = controller.getBookFlightsSimpleDetailsByFlightName(nickname);
        return baseBookFlightDTOS.stream()
                .map(BookFlightSoapMapper::toSoapBaseBookFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public SoapBookFlightDTO getBookFlightDetailsById(Long id) {
        BookFlightDTO bookFlightDTO= controller.getBookFlightDetailsById(id);
        return BookFlightSoapMapper.toSoapBookFlightDTO(bookFlightDTO);
    }
}
