package app.adapters.soap.booking;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import app.adapters.dto.bookflight.SoapBookFlightDTO;
import app.adapters.dto.ticket.TicketLuggageArray;
import app.adapters.dto.ticket.TicketWithLuggage;
import app.adapters.mappers.BookFlightSoapMapper;
import app.adapters.mappers.TicketLuggageMapper;
import app.adapters.soap.BaseSoapAdapter;
import controllers.auth.IAuthController;
import controllers.booking.IBookingController;
import controllers.booking.IBookingSoapController;
import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.wsdl11.SOAPBindingUtil;

import javax.xml.namespace.QName;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class BookingSoapAdapter extends BaseSoapAdapter implements IBookingSoapController {

    private final IBookingController controller;
    private final IAuthController authController;

    public BookingSoapAdapter(IBookingController controller, IAuthController authController) {
        this.controller = controller;
        this.authController = authController;
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

    @Override
    @WebMethod
    public SoapBaseBookFlightDTO completeBooking(Long bookingId,String token) {
        String nicknameFromToken = authController.getNicknameFromToken(token);

        String nicknameFromBookflight = controller.getCustomerNicknameByBookingId(bookingId);

        if(!nicknameFromToken.equals(nicknameFromBookflight)){
            throw createSoapFault("El usuario no está autorizado para completar esta reserva.");
        }
        if(nicknameFromBookflight == null || nicknameFromBookflight.isEmpty()){
            throw createSoapFault("La reserva no existe.");
        }

        BaseBookFlightDTO bookFlightDTO= controller.completeBooking(bookingId);
        return BookFlightSoapMapper.toSoapBaseBookFlightDTO(bookFlightDTO);
    }

    @Override
    @WebMethod
    public SoapBaseBookFlightDTO cancelBooking(Long bookingId) {
        BaseBookFlightDTO bookFlightDTO= controller.cancelBooking(bookingId);
        return BookFlightSoapMapper.toSoapBaseBookFlightDTO(bookFlightDTO);
    }

    private SOAPFaultException createSoapFault(String message) {
        try {
            SOAPFactory factory = SOAPFactory.newInstance();
            SOAPFault fault = factory.createFault(message, new QName("Server"));
            return new SOAPFaultException(fault);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear SOAPFaultException", e);
        }
    }
}
