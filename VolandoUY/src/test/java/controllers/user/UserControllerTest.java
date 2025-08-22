package controllers.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import factory.UserFactoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private IUserService usuarioService;
    private ModelMapper modelMapper;
    private UserMapper userMapper;
    private UserFactoryMapper userFactoryMapper;
    private IUserController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUserService.class);
        modelMapper = mock(ModelMapper.class);
        userMapper = mock(UserMapper.class);
        userFactoryMapper = mock(UserFactoryMapper.class);
        usuarioController = new UserController(usuarioService);
    }

    @Test
    @DisplayName("Debe llamar a registerCustomer y mapear correctamente el DTO")
    void registerCustomer_shouldCallToServiceWithMappedEntity() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname("gyabisito");
        customerDTO.setName("Jose");
        customerDTO.setSurname("Ramirez"); // <- obligatorio
        customerDTO.setMail("gyabisito@example.com");
        customerDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        customerDTO.setIdType(EnumTipoDocumento.CI);
        customerDTO.setId("12345678");
        customerDTO.setCitizenship("Uruguay");

        Customer customerMock = new Customer();
        customerMock.setNickname("gyabisito");

        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customerMock);

        usuarioController.registerCustomer(customerDTO);

        verify(usuarioService).registerCustomer(customerDTO);
    }

    @Test
    @DisplayName("Debe llamar a createCategory y mapear correctamente el DTO")
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


    @Test
    @DisplayName("Debe retornar todos los usuarios desde el service")
    void obtenerTodosLosUsuarios_deberiaRetornarListaDelService() {
        UserDTO usuario = new CustomerDTO();
        usuario.setNickname("gyabisito");

        when(usuarioService.getAllUsers()).thenReturn(List.of(usuario));

        List<UserDTO> resultado = usuarioController.getAllUsers();

        assertEquals(1, resultado.size());
        assertEquals("gyabisito", resultado.get(0).getNickname());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si no hay usuarios")
    void obtenerTodosLosUsuarios_listaVacia() {

        when(usuarioService.getAllUsers()).thenReturn(Collections.emptyList());

        List<UserDTO> resultado = usuarioController.getAllUsers();

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe retornar todos los nicknames de usuarios desde el service")
    void getAllUsersNicknames_shouldReturnFromService() {
        // GIVEN
        List<String> expectedNicknames = List.of("gyabisito", "flyuy");
        when(usuarioService.getAllUsersNicknames()).thenReturn(expectedNicknames);

        // WHEN
        List<String> result = usuarioController.getAllUsersNicknames();

        // THEN
        assertEquals(expectedNicknames, result);
    }

    @Test
    @DisplayName("Debe retornar todos los nicknames de aerolíneas desde el service")
    void getAllAirlinesNicknames_shouldReturnMappedNicknames() {
        // GIVEN
        AirlineDTO airline1 = new AirlineDTO();
        airline1.setNickname("air1");

        AirlineDTO airline2 = new AirlineDTO();
        airline2.setNickname("air2");

        when(usuarioService.getAllAirlines()).thenReturn(List.of(airline1, airline2));

        // WHEN
        List<String> result = usuarioController.getAllAirlinesNicknames();

        // THEN
        assertEquals(List.of("air1", "air2"), result);
    }

    @Test
    @DisplayName("Debe retornar el usuario correspondiente por nickname")
    void getUserByNickname_shouldReturnUserDTO() {
        // GIVEN
        UserDTO user = new CustomerDTO();
        user.setNickname("gyabisito");

        when(usuarioService.getUserByNickname("gyabisito")).thenReturn(user);

        // WHEN
        UserDTO result = usuarioController.getUserByNickname("gyabisito");

        // THEN
        assertNotNull(result);
        assertEquals("gyabisito", result.getNickname());
    }

    @Test
    @DisplayName("Debe actualizar el usuario correctamente")
    void updateUser_shouldDelegateToService() {
        // GIVEN
        UserDTO updated = new CustomerDTO();
        updated.setNickname("gyabisito");

        when(usuarioService.updateUser("gyabisito", updated)).thenReturn(updated);

        // WHEN
        UserDTO result = usuarioController.updateUser("gyabisito", updated);

        // THEN
        assertNotNull(result);
        assertEquals("gyabisito", result.getNickname());
    }

    @Test
    @DisplayName("Debe retornar AirlineDTO por nickname")
    void getAirlineByNickname_shouldReturnFromService() {
        // GIVEN
        AirlineDTO airline = new AirlineDTO();
        airline.setNickname("flyuy");

        when(usuarioService.getAirlineDetailsByNickname("flyuy")).thenReturn(airline);

        // WHEN
        AirlineDTO result = usuarioController.getAirlineByNickname("flyuy");

        // THEN
        assertNotNull(result);
        assertEquals("flyuy", result.getNickname());
    }

}
