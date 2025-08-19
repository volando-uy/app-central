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
import factory.UserFactoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserTest {
    private IUserService usuarioService;
    private ModelMapper modelMapper;
    private UserMapper userMapper;
    private UserFactoryMapper userFactoryMapper;
    private IUserController usuarioController;


    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        userMapper = new UserMapper(modelMapper);
        usuarioService = new UserService(modelMapper, userMapper); // o podés mockearlo si querés
        usuarioController = new UserController(usuarioService);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname("Nickname");
        customerDTO.setMail("mail@gmail.com");
        customerDTO.setName("Customer");
        customerDTO.setSurname("Apellido");
        customerDTO.setCitizenship("Nacionalidad");
        customerDTO.setId("55906938");
        customerDTO.setIdType(EnumTipoDocumento.CI);
        customerDTO.setBirthDate(LocalDate.now());
        usuarioController.registerCustomer(customerDTO);
    }

    @Test
    public void modificarDatosCliente() {
        // Paso 1: Listar usuarios (ya hay uno cargado en @BeforeEach)
        List<String> nicknames = usuarioController.getAllUsersNicknames();
        assertFalse(nicknames.isEmpty());

        // Paso 2: Seleccionar usuario original
        String nickname = nicknames.get(0);
        UserDTO originalUserDTO = usuarioController.getUserByNickname(nickname);
        assertEquals("Nickname", originalUserDTO.getNickname());
        System.out.println("Original: " + originalUserDTO);

        // Paso 3: Crear versión temp modificada
        CustomerDTO modifiedCustomerDTO = new CustomerDTO(); // O usar un UserDTO si estás usando uno genérico
        modifiedCustomerDTO.setNickname(originalUserDTO.getNickname()); // campo que no cambia
        modifiedCustomerDTO.setMail(originalUserDTO.getMail());         // campo que no cambia
        modifiedCustomerDTO.setName("NuevoNombre");
        modifiedCustomerDTO.setSurname("NuevoApellido");
        modifiedCustomerDTO.setCitizenship("Uruguaya");
        modifiedCustomerDTO.setId("987654321");
        modifiedCustomerDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        modifiedCustomerDTO.setIdType(EnumTipoDocumento.RUT);

        UserDTO temp = usuarioController.updateTemporalUser(modifiedCustomerDTO);
        System.out.println("No Temporal: " + originalUserDTO);
        System.out.println("Temporal: " + temp);

        // Paso 4: Confirmar edición
        usuarioController.updateUser(nickname, temp);

        // Paso 5: Volver a obtener y verificar cambios
        CustomerDTO finalDTO = (CustomerDTO) usuarioController.getUserByNickname(nickname);
        System.out.println("Final: " + finalDTO);

        assertEquals("NuevoNombre", finalDTO.getName());
        assertEquals("NuevoApellido", finalDTO.getSurname());
        assertEquals("Uruguaya", finalDTO.getCitizenship());
        assertEquals("987654321", finalDTO.getId());
        assertEquals(EnumTipoDocumento.RUT, finalDTO.getIdType());

        // Verificar campos inmutables
        assertEquals("Nickname", finalDTO.getNickname());
        assertEquals("mail@gmail.com", finalDTO.getMail());
    }

    @Test
    public void updateAirline() {
        // Paso 1: Crear una aerolínea originalAirlineDTO y darla de alta
        AirlineDTO originalAirlineDTO = new AirlineDTO();
        originalAirlineDTO.setNickname("FlyTest");
        originalAirlineDTO.setMail("a@gmail.com");
        originalAirlineDTO.setName("NombreOriginal");
        originalAirlineDTO.setWeb("webOriginal.com");
        originalAirlineDTO.setDescription("Descripcion originalAirlineDTO");

        usuarioController.registerAirline(originalAirlineDTO);

        // Paso 2: Obtener el usuario originalAirlineDTO
        List<String> nicknames = usuarioController.getAllUsersNicknames();
        assertFalse(nicknames.isEmpty());

        String nickname = nicknames.get(1);
        UserDTO usuarioOriginalDTO = usuarioController.getUserByNickname(nickname);
        assertEquals("FlyTest", usuarioOriginalDTO.getNickname());
        System.out.println("Original: " + usuarioOriginalDTO);

        // Paso 3: Crear versión temporal modifiedAirlineDTO
        AirlineDTO modifiedAirlineDTO = new AirlineDTO();
        modifiedAirlineDTO.setNickname(usuarioOriginalDTO.getNickname()); // campo inmutable
        modifiedAirlineDTO.setMail(usuarioOriginalDTO.getMail());         // campo inmutable
        modifiedAirlineDTO.setName("NombreTEMP");
        modifiedAirlineDTO.setWeb("webTEMP.com");
        modifiedAirlineDTO.setDescription("DescripcionTEMP");

        UserDTO temporal = usuarioController.updateTemporalUser(modifiedAirlineDTO);
        System.out.println("No Temporal: " + originalAirlineDTO);
        System.out.println("Temporal: " + temporal);

        // Paso 4: Confirmar edición
        usuarioController.updateUser(nickname, temporal);

        // Paso 5: Verificar cambios
        AirlineDTO finalDTO = (AirlineDTO) usuarioController.getUserByNickname(nickname);
        System.out.println("Final: " + finalDTO);

        assertEquals("NombreTEMP", finalDTO.getName());
        assertEquals("webTEMP.com", finalDTO.getWeb());
        assertEquals("DescripcionTEMP", finalDTO.getDescription());

        // Verificar campos inmutables
        assertEquals("FlyTest", finalDTO.getNickname());
        assertEquals("a@gmail.com", finalDTO.getMail());
    }
//
//    public void updateAirline () {
//        Airline aerolinea = createAirline("test");
//
//        //Given
//        AirlineDTO aerolineaTempDTO = new AirlineDTO();
//        aerolineaTempDTO.setNickname(aerolinea.getNickname());
//        aerolineaTempDTO.setMail(aerolinea.getMail());
//
//
//        //When
//        when(modelMapper.map(aerolinea, Airline.class)).thenReturn(aerolinea);
//        when(usuarioService.getAllUsers()).thenReturn(List.of(modelMapper.map(aerolinea, AirlineDTO.class)));
//        when(usuarioService.getUserByNickname(aerolinea.getNickname())).thenReturn(modelMapper.map(aerolinea, AirlineDTO.class);
//
//        //Then
//        // Ya es seguro modificar AerolineTemp
//        aerolineaTempDTO.setNombre("NombreTEMP");
//        aerolineaTempDTO.setWeb("webTEMP");
//        aerolineaTempDTO.setDescripcion("descripcionTEMP");
//
//        System.out.println("Airline " + aerolinea);
//        System.out.println("AerolineaTemp " + aerolineaTempDTO);
//
//        //Supongamos que desea confirmar
//        aerolinea.updateDataFrom(aerolineaTempDTO);
//        assertEquals(modelMapper.map(aerolineaTempDTO, Airline.class), aerolinea);
//    }

    Customer createCustomer(String nick) {
        Customer c = new Customer();
        c.setNickname(nick);
        c.setName("Juan");
        c.setSurname("Pérez");
        c.setMail("juan@example.com");
        c.setId("123");
        c.setBirthDate(LocalDate.of(1990, 1, 1));
        c.setIdType(EnumTipoDocumento.CI);
        return c;
    }


    Airline createAirline(String nick) {
        Airline a = new Airline();
        a.setNickname(nick);
        a.setName("Juan");
        a.setWeb("www.google.com");
        a.setDescription("desc");
        a.setMail("a@gmail.com");
        return a;
    }
}