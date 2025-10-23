package controllers.user;

import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import domain.services.user.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {


    @Mock
    private IUserService mockUserService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(mockUserService); // 💉 inyectás el mock
    }


    @Test
    void registerCustomer_shouldPersistCustomer() {
        BaseCustomerDTO inputDTO = new BaseCustomerDTO();
        inputDTO.setNickname("jose");

        CustomerDTO expectedDTO = new CustomerDTO();
        expectedDTO.setNickname("jose");

        when(mockUserService.registerCustomer(any(), any())).thenReturn(expectedDTO);

        BaseCustomerDTO result = userController.registerCustomer(inputDTO, null);

        assertNotNull(result);
        assertEquals("jose", result.getNickname());
    }

    @Test
    void registerAirline_shouldPersistAirline() {
        BaseAirlineDTO input = new BaseAirlineDTO();
        input.setNickname("airTest");

        AirlineDTO expected = new AirlineDTO();
        expected.setNickname("airTest");

        when(mockUserService.registerAirline(any(), any())).thenReturn(expected);

        BaseAirlineDTO result = userController.registerAirline(input, null);

        assertNotNull(result);
        assertEquals("airTest", result.getNickname());
    }

    @Test
    @DisplayName("Debe actualizar un usuario")
    void updateUser_shouldWorkCorrectly() {
        // Arrange - datos del usuario actualizado
        CustomerDTO input = new CustomerDTO();
        input.setNickname("gyabisito");
        input.setName("NuevoNombre");

        CustomerDTO updated = new CustomerDTO();
        updated.setNickname("gyabisito");
        updated.setName("NuevoNombre");
        updated.setSurname("Ramirez"); // opcional, para demostrar que no se pierde

        // Configuramos el mock para que devuelva el usuario actualizado
        when(mockUserService.updateUser(eq("gyabisito"), any(CustomerDTO.class), any()))
                .thenReturn(updated);

        // Act - llamamos al controller
        UserDTO result = userController.updateUser("gyabisito", input, null);

        // Assert - validamos que el resultado es correcto
        assertNotNull(result);
        assertEquals("NuevoNombre", result.getName());
        assertEquals("gyabisito", result.getNickname());

        // Verificamos que se haya llamado el método del mock
        verify(mockUserService).updateUser(eq("gyabisito"), any(CustomerDTO.class), any());
    }


    @Test
    @DisplayName("Debe retornar todos los detalles simples de los usuarios")
    void getAllUsersSimpleDetails_shouldReturnList() {
        List<UserDTO> mockList = List.of(new BaseCustomerDTO(), new BaseAirlineDTO());
        when(mockUserService.getAllUsers(false)).thenReturn(mockList);

        List<UserDTO> result = userController.getAllUsersSimpleDetails();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Debe retornar todos los detalles completos de los usuarios")
    void getAllUsersDetails_shouldReturnList() {
        List<UserDTO> mockList = List.of(new CustomerDTO(), new AirlineDTO());
        when(mockUserService.getAllUsers(true)).thenReturn(mockList);

        List<UserDTO> result = userController.getAllUsersDetails();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Debe retornar nicknames de todos los usuarios")
    void getAllUsersNicknames_shouldReturnList() {
        when(mockUserService.getAllUsersNicknames()).thenReturn(List.of("gyabisito", "flyuy"));

        List<String> result = userController.getAllUsersNicknames();

        assertTrue(result.contains("gyabisito"));
        assertTrue(result.contains("flyuy"));
    }

    @Test
    @DisplayName("Debe retornar nicknames de todas las aerolíneas")
    void getAllAirlinesNicknames_shouldReturnList() {
        when(mockUserService.getAllAirlinesDetails(false)).thenReturn(
                List.of(
                        new AirlineDTO("flyuy", "FlyUY", "mail", "pass", null, "desc", "web"),
                        new AirlineDTO("aeroarg", "AeroArg", "mail", "pass", null, "desc", "web")
                )
        );

        List<String> result = userController.getAllAirlinesNicknames();

        assertEquals(2, result.size());
        assertTrue(result.contains("flyuy"));
    }

    @Test
    @DisplayName("Debe retornar detalles simples de todas las aerolíneas")
    void getAllAirlinesSimpleDetails_shouldReturnList() {
        when(mockUserService.getAllAirlinesDetails(false)).thenReturn(List.of(new AirlineDTO()));

        List<BaseAirlineDTO> result = userController.getAllAirlinesSimpleDetails();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar detalles completos de todas las aerolíneas")
    void getAllAirlinesDetails_shouldReturnList() {
        when(mockUserService.getAllAirlinesDetails(true)).thenReturn(List.of(new AirlineDTO()));

        List<AirlineDTO> result = userController.getAllAirlinesDetails();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar detalles simples de todos los customers")
    void getAllCustomersSimpleDetails_shouldReturnList() {
        CustomerDTO customerDTO = new CustomerDTO();
        when(mockUserService.getAllCustomersDetails(false)).thenReturn(List.of(customerDTO));


        List<BaseCustomerDTO> result = userController.getAllCustomersSimpleDetails();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar detalles completos de todos los customers")
    void getAllCustomersDetails_shouldReturnList() {
        when(mockUserService.getAllCustomersDetails(true)).thenReturn(List.of(new CustomerDTO()));

        List<CustomerDTO> result = userController.getAllCustomersDetails();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe retornar detalles simples de un customer por nickname")
    void getCustomerSimpleDetailsByNickname_shouldWork() {
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("gyabisito");

        when(mockUserService.getCustomerDetailsByNickname("gyabisito", false)).thenReturn(customer);

        BaseCustomerDTO result = userController.getCustomerSimpleDetailsByNickname("gyabisito");

        assertNotNull(result);
        assertEquals("gyabisito", result.getNickname());
    }


    @Test
    @DisplayName("Debe retornar detalles completos de un customer por nickname")
    void getCustomerDetailsByNickname_shouldWork() {
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("gyabisito");

        when(mockUserService.getCustomerDetailsByNickname("gyabisito", true)).thenReturn(customer);

        CustomerDTO result = userController.getCustomerDetailsByNickname("gyabisito");

        assertEquals("gyabisito", result.getNickname());
    }

    @Test
    @DisplayName("Debe retornar detalles simples de una aerolínea por nickname")
    void getAirlineSimpleDetailsByNickname_shouldWork() {
        AirlineDTO airline = new AirlineDTO();
        airline.setNickname("flyuy");

        when(mockUserService.getAirlineDetailsByNickname("flyuy", false)).thenReturn(airline);

        BaseAirlineDTO result = userController.getAirlineSimpleDetailsByNickname("flyuy");

        assertNotNull(result);
        assertEquals("flyuy", result.getNickname());
    }


    @Test
    @DisplayName("Debe retornar detalles completos de una aerolínea por nickname")
    void getAirlineDetailsByNickname_shouldWork() {
        AirlineDTO airline = new AirlineDTO();
        airline.setNickname("flyuy");

        when(mockUserService.getAirlineDetailsByNickname("flyuy", true)).thenReturn(airline);

        AirlineDTO result = userController.getAirlineDetailsByNickname("flyuy");

        assertEquals("flyuy", result.getNickname());
    }

    @Test
    @DisplayName("Debe retornar detalles simples de un usuario genérico por nickname")
    void getUserSimpleDetailsByNickname_shouldWork() {
        BaseCustomerDTO customer = new BaseCustomerDTO();
        customer.setNickname("gyabisito");

        when(mockUserService.getUserDetailsByNickname("gyabisito", false)).thenReturn(customer);

        UserDTO result = userController.getUserSimpleDetailsByNickname("gyabisito");

        assertEquals("gyabisito", result.getNickname());
    }
}
