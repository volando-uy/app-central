package domain.services.booking;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.luggage.*;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
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
import factory.RepositoryFactory;
import infra.repository.booking.BookingRepository;
import infra.repository.booking.IBookingRepository;
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
    @Setter
    private IBookingRepository bookingRepository;

    private CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public BookingService() {
    }

    @Override
    public BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO,
                                           Map<BaseTicketDTO, List<LuggageDTO>> tickets,
                                           String userNickname,
                                           String flightName) {
        BookFlight bookFlight = customModelMapper.map(bookingDTO, BookFlight.class);
        bookFlight.setTotalPrice(0.0);
        bookFlight.setTickets(new ArrayList<>());
        ValidatorUtil.validate(bookFlight);

        if (tickets == null || tickets.isEmpty())
            throw new UnsupportedOperationException("No se pueden crear reservas sin tickets");

        // Tira throw si no existe
        Flight flight = flightService.getFlightByName(flightName, true);

        // Tira throw si no existe
        Customer customer = userService.getCustomerByNickname(userNickname,true);

        FlightRoute flightRoute = flight.getFlightRoute();

        List<Seat> availableSeats = seatService.getLimitedAvailableSeatsByFlightNameAndSeatType(flightName, tickets.size(), bookingDTO.getSeatType());
        if (availableSeats == null || availableSeats.isEmpty())
            throw new UnsupportedOperationException("No hay asientos disponibles");
        if (availableSeats.size() < tickets.size())
            throw new UnsupportedOperationException("No hay suficientes asientos disponibles");

        List<Ticket> savedTickets = new ArrayList<>();
        for (BaseTicketDTO ticketDTO : tickets.keySet()) {
            Ticket t = ticketService.createTicketWithoutPersistence(ticketDTO);

            List<LuggageDTO> lugDTOs = tickets.get(ticketDTO);
            if (lugDTOs != null) {
                for (LuggageDTO ldto : lugDTOs) {
                    t.addLuggage(mapToLuggageEntity(ldto));

                    if (ldto instanceof ExtraLuggageDTO)
                        bookFlight.setTotalPrice(bookingDTO.getTotalPrice() + flightRoute.getPriceExtraUnitBaggage());
                }
            }

            // Agregamos el precio del asiento
            bookFlight.setTotalPrice(
                    bookFlight.getSeatType() == EnumTipoAsiento.TURISTA
                            ? bookFlight.getTotalPrice() + flightRoute.getPriceTouristClass()
                            : bookFlight.getTotalPrice() + flightRoute.getPriceBusinessClass()
            );



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

        if (dto instanceof BaseBasicLuggageDTO b) {
            BasicLuggage l =  customModelMapper.map(b, BasicLuggage.class);
            l.setWeight(b.getWeight());
            l.setTicket(null);
            return l;
        }
        if (dto instanceof BaseExtraLuggageDTO e) {
            ExtraLuggage l =  customModelMapper.map(e, ExtraLuggage.class);
            l.setWeight(e.getWeight());
            l.setTicket(null);
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
