package casosdeuso;

import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CategoryDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.enums.EnumTipoDocumento;
import domain.services.user.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterUserTest {

    @Mock
    private IUserService usuarioService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private IUserController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUserService.class);
        modelMapper = mock(ModelMapper.class);
        usuarioController = new UserController(usuarioService);
    }

    @Test
    @DisplayName("Debe llamar a registerCustomer y mapear correctamente el DTO")
    void registerCustomer_shouldCallToServiceWithMappedEntity() {
        CategoryDTO customerDTO = new CategoryDTO();
        customerDTO.setNickname("gyabisito");
        customerDTO.setName("Jose");
        customerDTO.setSurname("Ramirez");
        customerDTO.setMail("gyabisito@example.com");
        customerDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        customerDTO.setIdType(EnumTipoDocumento.CI);
        customerDTO.setId("01234567");
        customerDTO.setCitizenship("Uruguay");

        Customer customerMock = new Customer();
        customerMock.setNickname("gyabisito");

        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customerMock);

        usuarioController.registerCustomer(customerDTO);

        verify(usuarioService).registerCustomer(customerDTO);
    }

    @Test
    @DisplayName("Debe llamar a registerAirline y mapear correctamente el DTO")
    void registerAirline_shouldCallToServiceWithMappedEntity() {
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("flyuy");
        airlineDTO.setName("FlyUY");
        airlineDTO.setMail("flyuy@correo.com");
        airlineDTO.setDescription("Low cost desde el cielo");

        Airline airlineMock = new Airline();
        airlineMock.setNickname("flyuy");

        when(modelMapper.map(airlineDTO, Airline.class)).thenReturn(airlineMock);

        usuarioController.registerAirline(airlineDTO);

        verify(usuarioService).registerAirline(airlineDTO);
    }

}
