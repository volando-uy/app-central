package domain.services.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de UserService")
class UserServiceTest {

    private ModelMapper modelMapper;
    private UserMapper userModelMapper;
    private UserService usuarioService;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        userModelMapper = new UserMapper(modelMapper);
        usuarioService = new UserService(modelMapper, userModelMapper);
    }

    CustomerDTO createCustomerDTO(String nick) {
        CustomerDTO c = new CustomerDTO();
        c.setNickname(nick);
        c.setName("TEST_CLIENTE");
        c.setSurname("TEST_APELLIDO");
        c.setMail("TEST@TEST.TEST");
        c.setId("11111111");
        c.setBirthDate(LocalDate.of(1990, 1, 1));
        c.setIdType(EnumTipoDocumento.CI);
        return c;
    }

    AirlineDTO createAirlineDTO(String nick) {
        AirlineDTO a = new AirlineDTO();
        a.setNickname(nick);
        a.setName("TEST_AEROLINEA");
        a.setMail("TEST@TEST.TEST");
        a.setDescription("TEST_DESCRIPCION");
        a.setWeb("www.TEST_WEB.com");
        return a;
    }

    @Nested
    @DisplayName("registerCustomer()")
    class RegisterCustomer {

        @Test
        @DisplayName("Debe agregar un nuevo cliente")
        void registerValidCustomer() {
            CustomerDTO customer = createCustomerDTO("gyabisito");
            usuarioService.registerCustomer(customer);

            List<UserDTO> usersDTOs = usuarioService.getAllUsers();
            assertEquals(1, usersDTOs.size());
            assertEquals("gyabisito", usersDTOs.get(0).getNickname());
        }

        @Test
        @DisplayName("No debe permitir nickname duplicado (case-insensitive)")
        void duplicatedNickname_throwException() {
            CustomerDTO firstCustomerDTO = createCustomerDTO("Gyabisito");
            CustomerDTO secondCustomerDTO = createCustomerDTO("gyabisito");

            usuarioService.registerCustomer(firstCustomerDTO);
            UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () ->
                    usuarioService.registerCustomer(secondCustomerDTO)
            );
            assertTrue(ex.getMessage().contains("gyabisito"));
        }
    }

    @Nested
    @DisplayName("registerAirline()")
    class RegisterAirline {

        @Test
        @DisplayName("Debe agregar una aerolínea correctamente")
        void registerAirline() {
            AirlineDTO aerolinea = createAirlineDTO("skyline");
            usuarioService.registerAirline(aerolinea);

            List<UserDTO> users = usuarioService.getAllUsers();
            assertEquals(1, users.size());
            assertEquals("skyline", users.get(0).getNickname());
        }
    }

    @Nested
    @DisplayName("getAllUsers()")
    class GetAllUsers {

        @Test
        @DisplayName("Debe retornar lista vacía al inicio")
        void checkEmptyListAtStart() {
            List<UserDTO> users = usuarioService.getAllUsers();
            assertTrue(users.isEmpty());
        }

        @Test
        @DisplayName("Debe retornar todos los usuarios agregados")
        void returnCreatedUsers() {
            usuarioService.registerCustomer(createCustomerDTO("uno"));
            usuarioService.registerAirline(createAirlineDTO("dos"));

            List<UserDTO> users = usuarioService.getAllUsers();
            assertEquals(2, users.size());
        }
    }
}
