package controllers.ticket;

import domain.dtos.ticket.TicketDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.constants.ErrorMessages;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 👉 Activa Mockito en JUnit 5
public class TicketControllerTest {

    @Mock
    private ITicketController iTicketController;

    private TicketDTO mockTicket;

    @BeforeEach
    public void setUp() {
        // Creamos un mock básico de TicketDTO
        mockTicket = new TicketDTO();
        mockTicket.setId(1L);
        // Puedes agregar más propiedades simuladas aquí si lo deseas
    }

    @Test
    @DisplayName("Debe devolver un ticket válido para ID existente")
    public void testGetTicketDetailsById() {
        when(iTicketController.getTicketDetailsById(1L)).thenReturn(mockTicket);

        TicketDTO result = iTicketController.getTicketDetailsById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Debe devolver un ticket simple válido para ID existente")
    public void testGetTicketSimpleDetailsById() {
        when(iTicketController.getTicketSimpleDetailsById(1L)).thenReturn(mockTicket);

        TicketDTO result = iTicketController.getTicketSimpleDetailsById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepción para ID inexistente en detalles completos")
    public void getTicketDetailsById_NonExistentId_ThrowError() {
        when(iTicketController.getTicketDetailsById(999L))
                .thenThrow(new IllegalArgumentException(ErrorMessages.ERROR_TICKET_NOT_FOUND));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            iTicketController.getTicketDetailsById(999L);
        });

        assertEquals(ErrorMessages.ERROR_TICKET_NOT_FOUND, exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción para ID inexistente en detalles simples")
    public void getTicketSimpleDetailsById_NonExistentId_ThrowError() {
        when(iTicketController.getTicketSimpleDetailsById(999L))
                .thenThrow(new IllegalArgumentException(ErrorMessages.ERROR_TICKET_NOT_FOUND));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            iTicketController.getTicketSimpleDetailsById(999L);
        });

        assertEquals(ErrorMessages.ERROR_TICKET_NOT_FOUND, exception.getMessage());
    }
}
