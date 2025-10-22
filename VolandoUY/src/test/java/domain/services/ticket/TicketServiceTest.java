package domain.services.ticket;

import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import infra.repository.ticket.ITicketRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketServiceTest {


    @Mock
    private ITicketRepository ticketRepository;

    @Mock
    private CustomModelMapper customModelMapper;

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        //  Inyectamos el mock del mapper
        ticketService = new TicketService(customModelMapper);
        ticketService.setTicketRepository(ticketRepository);
    }

    @Test
    @DisplayName("Debe devolver TicketDTO completo cuando el ticket existe (full=true)")
    void testGetTicketDetailsById_Full_True() {
        // Arrange - Ticket simulado
        Ticket mockTicket = new Ticket();
        mockTicket.setId(1L);

        // Si tu ModelMapper accede a ticket.getSeat().getNumber(), esto NO puede ser null
        Seat seat = new Seat();
        seat.setId(100L);
        seat.setNumber("22B");
        mockTicket.setSeat(seat);

        // Para evitar problemas, también podés configurar bookFlight si es necesario
        BookFlight bookFlight = new BookFlight();
        bookFlight.setId(200L);
        mockTicket.setBookFlight(bookFlight);

        // Arrange - TicketDTO esperado
        TicketDTO mockTicketDTO = new TicketDTO();
        mockTicketDTO.setId(1L);
        mockTicketDTO.setSeatId(100L);
        mockTicketDTO.setSeatNumber("22B");
        mockTicketDTO.setBookFlightId(200L);
        mockTicketDTO.setBasicLuggages(List.of());  // simulamos vacío
        mockTicketDTO.setExtraLuggages(List.of());  // simulamos vacío

        when(ticketRepository.getFullTicketById(1L)).thenReturn(mockTicket);
        when(customModelMapper.mapFullTicket(mockTicket)).thenReturn(mockTicketDTO);

        // Act
        TicketDTO result = ticketService.getTicketDetailsById(1L, true);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getSeatId());
        assertEquals("22B", result.getSeatNumber());
        assertEquals(200L, result.getBookFlightId());
        assertTrue(result.getBasicLuggages().isEmpty());
        assertTrue(result.getExtraLuggages().isEmpty());

        verify(ticketRepository).getFullTicketById(1L);
        verify(customModelMapper).mapFullTicket(mockTicket);
    }

    @Test
    @DisplayName("Debe devolver TicketDTO simple cuando el ticket existe (full=false)")
    void testGetTicketDetailsById_Full_False() {
        // Arrange
        Ticket mockTicket = new Ticket();
        mockTicket.setId(1L);

        TicketDTO mockTicketDTO = new TicketDTO();
        mockTicketDTO.setId(1L);

        when(ticketRepository.findByKey(1L)).thenReturn(mockTicket);
        when(customModelMapper.map(mockTicket, TicketDTO.class)).thenReturn(mockTicketDTO);

        // Act
        TicketDTO result = ticketService.getTicketDetailsById(1L, false);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(ticketRepository).findByKey(1L);
        verify(customModelMapper).map(mockTicket, TicketDTO.class);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el ticket no existe (full=true)")
    void testGetTicketDetailsById_Full_True_NotFound() {
        // Arrange
        when(ticketRepository.getFullTicketById(999L)).thenReturn(null);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.getTicketDetailsById(999L, true);
        });

        assertEquals(ErrorMessages.ERROR_TICKET_NOT_FOUND, exception.getMessage());
        verify(ticketRepository).getFullTicketById(999L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el ticket no existe (full=false)")
    void testGetTicketDetailsById_Full_False_NotFound() {
        // Arrange
        when(ticketRepository.findByKey(999L)).thenReturn(null);

        // Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ticketService.getTicketDetailsById(999L, false);
        });

        assertEquals(ErrorMessages.ERROR_TICKET_NOT_FOUND, exception.getMessage());
        verify(ticketRepository).findByKey(999L);
    }

    @Test
    @DisplayName("createTicketWithoutPersistence debe crear un ticket mapeado sin dependencias")
    void testCreateTicketWithoutPersistence() {
        // Arrange
        BaseTicketDTO baseTicketDTO = new BaseTicketDTO();
        baseTicketDTO.setId(10L);

        Ticket mappedTicket = new Ticket();
        mappedTicket.setId(10L);

        when(customModelMapper.map(baseTicketDTO, Ticket.class)).thenReturn(mappedTicket);

        // Act
        Ticket result = ticketService.createTicketWithoutPersistence(baseTicketDTO);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertNull(result.getSeat());
        assertNull(result.getBookFlight());
        assertNull(result.getBasicLuggages());
        assertNull(result.getExtraLuggages());

        verify(customModelMapper).map(baseTicketDTO, Ticket.class);
    }
}
