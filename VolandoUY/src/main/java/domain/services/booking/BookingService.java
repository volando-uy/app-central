package domain.services.booking;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.flight.Flight;
import domain.models.luggage.Luggage;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import domain.services.flight.IFlightService;
import domain.services.seat.ISeatService;
import domain.services.ticket.ITicketService;
import domain.services.ticket.TicketService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import infra.repository.booking.BookingRepository;
import lombok.Setter;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingService implements IBookingService {
    @Setter
    private ISeatService seatService;
    @Setter
    private IFlightService flightService;
    @Setter
    private ITicketService ticketService;
    @Setter
    private IUserService userService;

    private BookingRepository bookingRepository;

    private CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public BookingService() {
        this.bookingRepository = new BookingRepository();
    }

    @Override
    public BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO,
                                           Map<BaseTicketDTO, List<LuggageDTO>> tickets,
                                           String userNickname,
                                           String flightName) {
        BookFlight bookFlight = customModelMapper.map(bookingDTO, BookFlight.class);
        ValidatorUtil.validate(bookFlight);

        if (tickets == null || tickets.isEmpty())
            throw new UnsupportedOperationException("No se pueden crear reservas sin tickets");

        Flight flight = flightService.getFlightByName(flightName, false);
        Customer customer = userService.getCustomerByNickname(userNickname,true);


        List<Seat> availableSeats = seatService.getLimitedAvailableSeatsByFlightName(flightName, tickets.size());
        if (availableSeats == null || availableSeats.isEmpty())
            throw new UnsupportedOperationException("No hay asientos disponibles");
        if (availableSeats.size() < tickets.size())
            throw new UnsupportedOperationException("No hay suficientes asientos disponibles");

        List<Ticket> savedTickets = new ArrayList<>();
        int idx = 0;
        for (BaseTicketDTO ticketDTO : tickets.keySet()) {
            Ticket t = ticketService.createTicketWithoutPersistence(ticketDTO);

            // no hace falta setear seat acá (lo hace el repo con el managedSeat)
            // pero si lo dejás, no pasa nada.

            List<LuggageDTO> lugDTOs = tickets.get(ticketDTO);
            if (lugDTOs != null) {
                for (LuggageDTO ldto : lugDTOs) {
                    t.addLuggage(mapToLuggageEntity(ldto));
                }
            }
            savedTickets.add(t);
        }
        // Asignamos el vuelo y el usuario (cliente) al BookFlight
        // Pasamos seats (detached) + tickets (transient). El repo los reatacha/persiste correctamente.
        bookingRepository.saveBookflightWithTicketsAndAddToSeats(bookFlight, savedTickets, availableSeats,customer);

        return customModelMapper.map(bookFlight, BaseBookFlightDTO.class);
    }

    /**
     * Mapea DTOs concretos a entidades concretas sin pasar por el tipo abstracto.
     */
    private Luggage mapToLuggageEntity(LuggageDTO dto) {
        if (dto == null) return null;

        if (dto instanceof BasicLuggageDTO b) {
            Luggage l =  customModelMapper.map(b, domain.models.luggage.BasicLuggage.class);
            l.setWeight(b.getWeight());
            return l;
        }
        if (dto instanceof ExtraLuggageDTO e) {
            Luggage l =  customModelMapper.map(e, domain.models.luggage.ExtraLuggage.class);
            l.setWeight(e.getWeight());
            return l;
        }
        throw new IllegalArgumentException("Tipo de LuggageDTO no soportado: " + dto.getClass().getName());
    }

    @Override
    public List<BookFlightDTO> getAllBookFlightsDetails(boolean full) {
        List<BookFlight> bookFlights = full ? bookingRepository.findFullAll()
                : bookingRepository.findAll();
        if (bookFlights == null || bookFlights.isEmpty()) {
            return List.of();
        }

        return bookFlights.stream()
                .map(bf -> full ? customModelMapper.mapFullBookFlight(bf) : customModelMapper.map(bf, BookFlightDTO.class))
                .toList();
    }
    @Override
    public List<BookFlightDTO> getAllBookFlightsDetailsByCustomerNickname(String nickname, boolean full) {
        List<BookFlight> bookFlights = full ? bookingRepository.findFullByCustomerNickname(nickname)
                : bookingRepository.findByCustomerNickname(nickname);
        if (bookFlights == null || bookFlights.isEmpty()) {
            return List.of();
        }

        return bookFlights.stream()
                .map(bf -> full ? customModelMapper.mapFullBookFlight(bf) : customModelMapper.map(bf, BookFlightDTO.class))
                .toList();
    }

    @Override
    public List<BookFlightDTO> getBookFlightsDetailsByFlightName(String flightName, boolean full) {
        List<BookFlight> bookFlights = full ? bookingRepository.findFullByFlightName(flightName)
                : bookingRepository.findByFlightName(flightName);
        if (bookFlights == null || bookFlights.isEmpty()) {
            return List.of();
        }

        return bookFlights.stream()
                .map(bf -> full ? customModelMapper.mapFullBookFlight(bf) : customModelMapper.map(bf, BookFlightDTO.class))
                .toList();
    }

    @Override
    public BookFlightDTO getBookFlightDetailsById(Long id, boolean full) {
        BookFlight bf = full ? bookingRepository.getFullBookingById(id) : bookingRepository.findByKey(id);
        if (bf == null) {
            return null;
        }

        return full ? customModelMapper.mapFullBookFlight(bf) : customModelMapper.map(bf, BookFlightDTO.class);
    }
}
