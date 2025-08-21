package casosdeuso;

import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
public class GetUserTest {
    private ModelMapper modelMapper = ControllerFactory.getModelMapper();
    private UserMapper userMapper = ControllerFactory.getUserMapper();
    private IUserService userService;
    private IUserController userController;

    @BeforeEach
    void setUp() {
        userService = new UserService(modelMapper, userMapper);
        userController = new UserController(userService);
    }


    @Test
    void getClientTest() {

        // Crear un nuevo clienteDTO
        CustomerDTO customerDTO = createCustomer("cliente");

        // Registrar el cliente usando el controlador
        userController.registerCustomer(customerDTO);

        // Obtener el cliente registrado
        UserDTO selectedUserDTO = userService.getUserByNickname("cliente");

        // Comprobar que no sea nulo y que el nickname es correcto
        assertNotNull(selectedUserDTO);
        assertEquals("cliente", selectedUserDTO.getNickname());
    }

    CustomerDTO createCustomer(String nick) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname(nick);
        customerDTO.setName("Juan");
        customerDTO.setSurname("Pérez");
        customerDTO.setMail("juan@example.com");
        customerDTO.setId("123");
        customerDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        customerDTO.setIdType(EnumTipoDocumento.CI);
        customerDTO.setCitizenship("Uruguayo");
        return customerDTO;
    }

}
