package domain.services.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.seat.Seat;
import domain.services.flight.IFlightService;
import domain.services.ticket.ITicketService;
import infra.repository.seat.ISeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.utils.CustomModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    ISeatRepository mockSeatRepository;

    @Mock
    CustomModelMapper mockMapper;

    @Mock
    ITicketService mockTicketService;

    @Mock
    IFlightService mockFlightService;

    SeatService seatService;

    @BeforeEach
    void setUp() {
        seatService = new SeatService(mockSeatRepository, mockMapper);
        seatService.setTicketService(mockTicketService);
        seatService.setFlightService(mockFlightService);
    }

    @Test
    void createSeatWithoutPersistance_shouldMapAndReturnSeat() {
        BaseSeatDTO dto = new BaseSeatDTO();
        Seat mappedSeat = new Seat();
        when(mockMapper.map(dto, Seat.class)).thenReturn(mappedSeat);

        Seat result = seatService.createSeatWithoutPersistance(dto);

        assertNotNull(result);
        assertEquals(mappedSeat, result);
        assertNull(result.getTicket());
        assertNull(result.getFlight());
        verify(mockMapper).map(dto, Seat.class);
    }

    @Test
    void assignTicketToSeat_shouldNotThrow() {
        // Método vacío: solo testeamos que no arroje excepción.
        assertDoesNotThrow(() -> seatService.assignTicketToSeat(1L, 1L));
    }

    @Test
    void getSeatById_shouldReturnSeatIfExists() {
        Seat seat = new Seat();
        seat.setId(1L);
        when(mockSeatRepository.findByKey(1L)).thenReturn(seat);

        Seat result = seatService.getSeatById(1L);

        assertEquals(1L, result.getId());
        verify(mockSeatRepository).findByKey(1L);
    }

    @Test
    void seatExists_shouldReturnTrueWhenSeatExists() {
        when(mockSeatRepository.findByKey(1L)).thenReturn(new Seat());

        assertTrue(seatService.seatExists(1L));
    }

    @Test
    void seatExists_shouldReturnFalseWhenSeatDoesNotExist() {
        when(mockSeatRepository.findByKey(99L)).thenReturn(null);

        assertFalse(seatService.seatExists(99L));
    }

    @Test
    void getAllSeatsByFlightName_shouldReturnSeatList() {
        List<Seat> seatList = List.of(new Seat(), new Seat());
        when(mockSeatRepository.getAllSeatsByFlightName("AA123")).thenReturn(seatList);

        List<Seat> result = seatService.getAllSeatsByFlightName("AA123");

        assertEquals(2, result.size());
        verify(mockSeatRepository).getAllSeatsByFlightName("AA123");
    }

    @Test
    void getLimitedAvailableSeatsByFlightNameAndSeatType_shouldReturnCorrectSeats() {
        EnumTipoAsiento seatType = EnumTipoAsiento.EJECUTIVO;
        List<Seat> seats = List.of(new Seat(), new Seat());
        when(mockSeatRepository.getLimitedAvailableSeatsByFlightNameAndSeatType("AA123", 2, seatType))
                .thenReturn(seats);

        List<Seat> result = seatService.getLimitedAvailableSeatsByFlightNameAndSeatType("AA123", 2, seatType);

        assertEquals(2, result.size());
        verify(mockSeatRepository).getLimitedAvailableSeatsByFlightNameAndSeatType("AA123", 2, seatType);
    }

    @Test
    void getSeatDetailsById_shouldReturnMappedDTO_whenFullFalse() {
        Seat seat = new Seat();
        when(mockSeatRepository.findByKey(1L)).thenReturn(seat);

        SeatDTO dto = new SeatDTO();
        when(mockMapper.map(seat, SeatDTO.class)).thenReturn(dto);

        SeatDTO result = seatService.getSeatDetailsById(1L, false);

        assertEquals(dto, result);
        verify(mockSeatRepository).findByKey(1L);
        verify(mockMapper).map(seat, SeatDTO.class);
    }

    @Test
    void getSeatDetailsById_shouldReturnMappedFullDTO_whenFullTrue() {
        Seat seat = new Seat();
        when(mockSeatRepository.getFullSeatById(1L)).thenReturn(seat);

        SeatDTO fullDto = new SeatDTO();
        when(mockMapper.mapFullSeat(seat)).thenReturn(fullDto);

        SeatDTO result = seatService.getSeatDetailsById(1L, true);

        assertEquals(fullDto, result);
        verify(mockSeatRepository).getFullSeatById(1L);
        verify(mockMapper).mapFullSeat(seat);
    }

    @Test
    void getSeatDetailsById_shouldReturnNull_whenSeatDoesNotExist() {
        when(mockSeatRepository.findByKey(999L)).thenReturn(null);

        SeatDTO result = seatService.getSeatDetailsById(999L, false);

        assertNull(result);
    }

    @Test
    void getSeatDetailsByTicketId_shouldReturnMappedDTO_whenFullFalse() {
        Seat seat = new Seat();
        when(mockSeatRepository.getSeatByTicketId(10L)).thenReturn(seat);

        SeatDTO dto = new SeatDTO();
        when(mockMapper.map(seat, SeatDTO.class)).thenReturn(dto);

        SeatDTO result = seatService.getSeatDetailsByTicketId(10L, false);

        assertEquals(dto, result);
        verify(mockSeatRepository).getSeatByTicketId(10L);
        verify(mockMapper).map(seat, SeatDTO.class);
    }

    @Test
    void getSeatDetailsByTicketId_shouldReturnMappedFullDTO_whenFullTrue() {
        Seat seat = new Seat();
        when(mockSeatRepository.getFullSeatByTicketId(10L)).thenReturn(seat);

        SeatDTO fullDto = new SeatDTO();
        when(mockMapper.mapFullSeat(seat)).thenReturn(fullDto);

        SeatDTO result = seatService.getSeatDetailsByTicketId(10L, true);

        assertEquals(fullDto, result);
        verify(mockSeatRepository).getFullSeatByTicketId(10L);
        verify(mockMapper).mapFullSeat(seat);
    }

    @Test
    void getSeatDetailsByTicketId_shouldReturnNull_whenSeatDoesNotExist() {
        when(mockSeatRepository.getSeatByTicketId(999L)).thenReturn(null);

        SeatDTO result = seatService.getSeatDetailsByTicketId(999L, false);

        assertNull(result);
    }
}
