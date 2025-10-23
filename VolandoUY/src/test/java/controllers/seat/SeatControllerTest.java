package controllers.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.services.seat.ISeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeatControllerTest {

    private ISeatService mockSeatService;
    private SeatController seatController;

    @BeforeEach
    void setUp() {
        mockSeatService = mock(ISeatService.class);
        seatController = new SeatController(mockSeatService);
    }

    @Test
    void getSeatDetailsById_shouldReturnFullSeatDTO() {
        SeatDTO mockSeat = new SeatDTO();
        mockSeat.setId(1L);

        when(mockSeatService.getSeatDetailsById(1L, true)).thenReturn(mockSeat);

        SeatDTO result = seatController.getSeatDetailsById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(mockSeatService).getSeatDetailsById(1L, true);
    }

    @Test
    void getSeatSimpleDetailsById_shouldReturnBaseSeatDTO() {
        BaseSeatDTO mockSeat = new BaseSeatDTO();
        mockSeat.setId(2L);

        SeatDTO detailedSeat = new SeatDTO();
        detailedSeat.setId(2L);

        when(mockSeatService.getSeatDetailsById(2L, false)).thenReturn(detailedSeat);

        BaseSeatDTO result = seatController.getSeatSimpleDetailsById(2L);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        verify(mockSeatService).getSeatDetailsById(2L, false);
    }

    @Test
    void getSeatSimpleDetailsByTicketId_shouldReturnBaseSeatDTO() {
        BaseSeatDTO mockSeat = new BaseSeatDTO();
        mockSeat.setId(3L);
        SeatDTO detailedSeat = new SeatDTO();
        detailedSeat.setId(3L);


        when(mockSeatService.getSeatDetailsByTicketId(3L, false)).thenReturn(detailedSeat);

        BaseSeatDTO result = seatController.getSeatSimpleDetailsByTicketId(3L);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        verify(mockSeatService).getSeatDetailsByTicketId(3L, false);
    }

    @Test
    void getSeatDetailsByTicketId_shouldReturnFullSeatDTO() {
        SeatDTO mockSeat = new SeatDTO();
        mockSeat.setId(4L);

        when(mockSeatService.getSeatDetailsByTicketId(4L, true)).thenReturn(mockSeat);

        SeatDTO result = seatController.getSeatDetailsByTicketId(4L);

        assertNotNull(result);
        assertEquals(4L, result.getId());
        verify(mockSeatService).getSeatDetailsByTicketId(4L, true);
    }
}
