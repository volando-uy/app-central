package casosdeuso;

import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterUserTest {

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
    @DisplayName("Debe crear el CustomerDTO")
    void registerCustomer_shouldCreateTheCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname("gyabisito");
        customerDTO.setName("Jose");
        customerDTO.setSurname("Ramirez");
        customerDTO.setMail("gyabisito@example.com");
        customerDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        customerDTO.setIdType(EnumTipoDocumento.CI);
        customerDTO.setId("01234567");
        customerDTO.setCitizenship("Uruguay");

        userController.registerCustomer(customerDTO);

        UserDTO createdCustomer = userController.getUserByNickname("gyabisito");

        // Verificar que el CustomerDTO fue creado correctamente
        assertEquals("gyabisito", createdCustomer.getNickname());
    }

    @Test
    @DisplayName("Debe llamar a createCategory y mapear correctamente el DTO")
    void registerAirline_shouldCreateTheAirline() {
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("flyuy");
        airlineDTO.setName("FlyUY");
        airlineDTO.setMail("flyuy@correo.com");
        airlineDTO.setDescription("Low cost desde el cielo");
        airlineDTO.setWeb("www.flyuy.com");

        userController.registerAirline(airlineDTO);

        AirlineDTO createdAirline = userController.getAirlineByNickname("flyuy");

        // Verificar que el AirlineDTO fue creado correctamente
        assertEquals("flyuy", createdAirline.getNickname());
    }

}
