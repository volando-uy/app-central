package domain.services.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.enums.EnumTipoDocumento;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import domain.models.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private ModelMapper modelMapper;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        modelMapper = mock(ModelMapper.class);
        userMapper = mock(UserMapper.class);
        userService = new UserService(modelMapper, userMapper);
    }

    @Test
    void registerCustomer_shouldAddCustomerSuccessfully_withAllFields() {
        CustomerDTO dto = new CustomerDTO(
                "nick1",
                "Pepe",
                "pepe@mail.com",
                "González",
                "Uruguay",
                LocalDate.of(1990, 1, 1),
                "12345678",
                EnumTipoDocumento.CI
        );

        Customer entity = new Customer();
        entity.setName("Pepe");
        entity.setNickname("nick1");
        entity.setMail("pepe@mail.com");
        entity.setSurname("González");
        entity.setCitizenship("Uruguay");
        entity.setBirthDate(LocalDate.of(1990, 1, 1));
        entity.setId("12345678");
        entity.setIdType(EnumTipoDocumento.CI);

        when(modelMapper.map(dto, Customer.class)).thenReturn(entity);
        when(modelMapper.map(entity, CustomerDTO.class)).thenReturn(dto);

        CustomerDTO result = userService.registerCustomer(dto);

        assertEquals("nick1", result.getNickname());
        assertEquals("González", result.getSurname());
        assertEquals("Uruguay", result.getCitizenship());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthDate());
        assertEquals("12345678", result.getId());
        assertEquals(EnumTipoDocumento.CI, result.getIdType());
    }

    @Test
    void registerCustomer_shouldThrowExceptionIfUserExists() {
        CustomerDTO dto = new CustomerDTO(
                "nick2",
                "pama",
                "pama@mail.com",
                "Pérez",
                "Argentina",
                LocalDate.of(1985, 5, 5),
                "87654321",
                EnumTipoDocumento.PASAPORTE
        );

        Customer entity = new Customer();
        entity.setName("pama");
        entity.setNickname("nick2");
        entity.setMail("pama@mail.com");
        entity.setSurname("Pérez");
        entity.setCitizenship("Argentina");
        entity.setBirthDate(LocalDate.of(1985, 5, 5));
        entity.setId("87654321");
        entity.setIdType(EnumTipoDocumento.PASAPORTE);

        // Simulamos dos llamadas a modelMapper.map(dto, Customer.class)
        when(modelMapper.map(dto, Customer.class))
                .thenReturn(entity) // primera llamada (registro original)
                .thenReturn(entity); // segunda llamada (para el duplicado)

        when(modelMapper.map(entity, CustomerDTO.class)).thenReturn(dto);

        // Primer registro debe pasar
        userService.registerCustomer(dto);

        // Segundo intento debe fallar
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            userService.registerCustomer(dto);
        });
        assertNotNull(ex.getMessage());
        assertFalse(ex.getMessage().isBlank());

        assertEquals(ErrorMessages.ERR_USUARIO_YA_EXISTE, ex.getMessage());
    }

    @Test
    void registerAirline_shouldRegisterSuccessfully() {
        AirlineDTO dto = new AirlineDTO("air123", "FlyHigh", "fly@mail.com", "Líder regional");
        Airline entity = new Airline("air123", "FlyHigh", "fly@mail.com", "Líder regional", "www.flyhigh.com");

        when(modelMapper.map(dto, Airline.class)).thenReturn(entity);
        when(modelMapper.map(entity, AirlineDTO.class)).thenReturn(dto);

        AirlineDTO result = userService.registerAirline(dto);

        assertEquals(dto.getNickname(), result.getNickname());
        assertEquals(dto.getName(), result.getName());
    }
    @Test
    void getUserByNickname_shouldReturnCustomerDTO() {
        CustomerDTO dto = new CustomerDTO(
                "nickCustomer",
                "pama",
                "pama@mail.com",
                "Sosa",
                "Uruguay",
                LocalDate.of(1995, 3, 15),
                "45612378",
                EnumTipoDocumento.CI
        );

        Customer entity = new Customer();
        entity.setName("pama");
        entity.setNickname("nickCustomer");
        entity.setMail("pama@mail.com");
        entity.setSurname("Sosa");
        entity.setBirthDate(LocalDate.of(1995, 3, 15));
        entity.setCitizenship("Uruguay");
        entity.setId("45612378");
        entity.setIdType(EnumTipoDocumento.CI);

        when(modelMapper.map(dto, Customer.class)).thenReturn(entity);
        when(userMapper.toDTO(entity)).thenReturn(dto);

        userService.registerCustomer(dto);

        UserDTO result = userService.getUserByNickname("nickCustomer");

        assertEquals("pama", result.getName());
        assertEquals("Sosa", ((CustomerDTO) result).getSurname());
    }



    @Test
    void getAllUsersNicknames_shouldReturnAllNicknames() {
        CustomerDTO dto = new CustomerDTO(
                "nick1", "Carlos", "carlos@mail.com",
                "Pérez", "Uruguay", LocalDate.of(1995, 1, 1), "12345678", EnumTipoDocumento.CI
        );

        Customer entity = new Customer();
        entity.setName("Carlos");
        entity.setNickname("nick1");
        entity.setMail("carlos@mail.com");
        entity.setSurname("Pérez");
        entity.setCitizenship("Uruguay");
        entity.setBirthDate(LocalDate.of(1995, 1, 1));
        entity.setId("12345678");
        entity.setIdType(EnumTipoDocumento.CI);

        when(modelMapper.map(dto, Customer.class)).thenReturn(entity);
        userService.registerCustomer(dto);

        List<String> nicknames = userService.getAllUsersNicknames();

        assertEquals(1, nicknames.size());
        assertTrue(nicknames.contains("nick1"));
    }

    @Test
    void getAllUsers_shouldReturnAllUsersMapped() {
        CustomerDTO dto = new CustomerDTO(
                "nick1", "Luis", "luis@mail.com",
                "Gómez", "Paraguay", LocalDate.of(1990, 5, 15), "99911122", EnumTipoDocumento.CI
        );

        Customer entity = new Customer();
        entity.setName("Luis");
        entity.setNickname("nick1");
        entity.setMail("luis@mail.com");
        entity.setSurname("Gómez");
        entity.setCitizenship("Paraguay");
        entity.setBirthDate(LocalDate.of(1990, 5, 15));
        entity.setId("99911122");
        entity.setIdType(EnumTipoDocumento.CI);

        when(modelMapper.map(dto, Customer.class)).thenReturn(entity);
        when(userMapper.toDTO(entity)).thenReturn(dto);

        userService.registerCustomer(dto);

        List<UserDTO> allUsers = userService.getAllUsers();

        assertEquals(1, allUsers.size());
        assertEquals("nick1", allUsers.get(0).getNickname());
    }

    @Test
    void getUserByNickname_shouldThrowExceptionIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserByNickname("inexistente");
        });
        assertTrue(ex.getMessage().contains("User no encontrado"));
    }

    @Test
    void updateUser_shouldUpdateCustomerData() {
        CustomerDTO original = new CustomerDTO(
                "cliente1",
                "Pedro",
                "pedro@mail.com",
                "López",
                "Chile",
                LocalDate.of(1980, 1, 1),
                "98765432",
                EnumTipoDocumento.CI
        );

        CustomerDTO updated = new CustomerDTO(
                "cliente1",  // mismo nickname
                "Pedro Actualizado",
                "pedro@mail.com",
                "García",
                "Argentina",
                LocalDate.of(1982, 2, 2),
                "99887766",
                EnumTipoDocumento.PASAPORTE
        );

        Customer originalEntity = new Customer();
        originalEntity.setName("Pedro");
        originalEntity.setNickname("cliente1");
        originalEntity.setMail("pedro@mail.com");
        originalEntity.setSurname("López");
        originalEntity.setBirthDate(LocalDate.of(1980, 1, 1));
        originalEntity.setCitizenship("Chile");
        originalEntity.setId("98765432");
        originalEntity.setIdType(EnumTipoDocumento.CI);

        Customer updatedEntity = new Customer();
        updatedEntity.setName("Pedro Actualizado");
        updatedEntity.setNickname("cliente1");
        updatedEntity.setMail("pedro@mail.com");
        updatedEntity.setSurname("García");
        updatedEntity.setBirthDate(LocalDate.of(1982, 2, 2));
        updatedEntity.setCitizenship("Argentina");
        updatedEntity.setId("99887766");
        updatedEntity.setIdType(EnumTipoDocumento.PASAPORTE);

        when(modelMapper.map(original, Customer.class)).thenReturn(originalEntity);
        when(userMapper.fromDTO(updated)).thenReturn(updatedEntity);
        when(userMapper.toDTO(originalEntity)).thenReturn(updated);

        userService.registerCustomer(original);
        UserDTO result = userService.updateUser("cliente1", updated);

        assertEquals("Pedro Actualizado", result.getName());
        assertEquals("Argentina", ((CustomerDTO) result).getCitizenship());
        assertEquals("99887766", ((CustomerDTO) result).getId());
    }
//    @Test
//    void updateUser_shouldUpdateSuccessfully() {
//        AirlineDTO originalDTO = new AirlineDTO(
//                "aerolinea1",
//                "OldName",
//                "aero@mail.com",
//                "Descripción válida con más de diez caracteres",
//                "www.old.com"
//
//        );
//
//        AirlineDTO updatedDTO = new AirlineDTO(
//                "aerolinea1",
//                "NewName",
//                "aero@mail.com",
//                "Nueva descripción válida con más de diez caracteres",
//                "www.new.com"
//        );
//
//        Airline airlineEntity = new Airline(
//                "aerolinea1",
//                "OldName",
//                "aero@mail.com",
//                "Descripción válida con más de diez caracteres",
//                "www.old.com"
//        );
//
//        Airline updatedAirlineEntity = new Airline(
//                "aerolinea1",
//                "NewName",
//                "aero@mail.com",
//                "Nueva descripción válida con más de diez caracteres",
//                "www.new.com"
//        );
//        when(modelMapper.map(originalDTO, Airline.class)).thenReturn(airlineEntity);
//        when(modelMapper.map(updatedDTO, Airline.class)).thenReturn(updatedAirlineEntity);
//        when(userMapper.toAirlineDTO(airlineEntity)).thenReturn(originalDTO);
//
//        when(userMapper.fromDTO(updatedDTO)).thenReturn(updatedAirlineEntity);
//        when(userMapper.toDTO(airlineEntity)).thenReturn(originalDTO);
//
//        when(userService.registerAirline(originalDTO)).thenReturn(originalDTO);
//        UserDTO result = userService.updateUser("aerolinea1", updatedDTO);
//        assertNotNull(result);
//        assertEquals("aerolinea1", result.getNickname());
//        assertEquals("NewName", result.getName());
//        assertEquals("Nueva descripción válida con más de diez caracteres", ((AirlineDTO) result).getDescription());
//        assertEquals("www.new.com", ((AirlineDTO) result).getWeb());
//    }
//


    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        AirlineDTO updatedDTO = new AirlineDTO("notFound", "Name", "email@mail.com", "desc", "web");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser("notFound", updatedDTO);
        });

        assertTrue(ex.getMessage().contains("User no encontrado"));
    }
    @Test
    void getAllAirlines_shouldReturnOnlyAirlines() {
        AirlineDTO dto1 = new AirlineDTO("air1", "Air1", "a1@mail.com", "desc12345678", "www.google.com");
        AirlineDTO dto2 = new AirlineDTO("air2", "Air2", "a2@mail.com", "desc12345678", "www.google.com");

        Airline entity1 = new Airline("air1", "Air1", "a1@mail.com", "desc223456789", "www.google.com");
        Airline entity2 = new Airline("air2", "Air2", "a2@mail.com", "desc23456789", "www.google.com");

        when(modelMapper.map(dto1, Airline.class)).thenReturn(entity1);
        when(modelMapper.map(dto2, Airline.class)).thenReturn(entity2);
        when(modelMapper.map(entity1, AirlineDTO.class)).thenReturn(dto1);
        when(modelMapper.map(entity2, AirlineDTO.class)).thenReturn(dto2);

        userService.registerAirline(dto1);
        userService.registerAirline(dto2);

        List<AirlineDTO> result = userService.getAllAirlines();

        assertEquals(2, result.size());
    }
    @Test
    void addFlightRouteToAirline_shouldAddRouteSuccessfully() {
        AirlineDTO dto = new AirlineDTO("skyline", "Skyline", "sky@mail.com", "Best airline", "www.skyline.com");
        Airline entity = new Airline("skyline", "Skyline", "sky@mail.com", "Best airline", "www.skyline.com");

        FlightRouteDTO routeDTO = new FlightRouteDTO();
        routeDTO.setName("Ruta 1");
        routeDTO.setDescription("Una ruta");
        routeDTO.setCreatedAt(LocalDate.now());
        routeDTO.setPriceTouristClass(100.0);
        routeDTO.setPriceBusinessClass(200.0);
        routeDTO.setPriceExtraUnitBaggage(30.0);

        FlightRoute routeEntity = new FlightRoute(
                "Ruta 1", "Una ruta", LocalDate.now(), 100.0, 200.0, 30.0
        );

        when(modelMapper.map(dto, Airline.class)).thenReturn(entity);
        when(modelMapper.map(routeDTO, FlightRoute.class)).thenReturn(routeEntity);
        when(modelMapper.map(routeEntity, FlightRouteDTO.class)).thenReturn(routeDTO);

        userService.registerAirline(dto);

        FlightRouteDTO result = userService.addFlightRouteToAirline("skyline", routeDTO);
        assertEquals("Ruta 1", result.getName());
    }
    @Test
    void addFlightRouteToAirline_shouldThrowIfUserNotAirline() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname("c123");
        customerDTO.setName("Cliente");
        customerDTO.setMail("cliente@mail.com");
        customerDTO.setSurname("Perez");
        customerDTO.setCitizenship("Uruguay");
        customerDTO.setBirthDate(LocalDate.of(1995, 1, 1));
        customerDTO.setId("12345678");
        customerDTO.setIdType(EnumTipoDocumento.CI);

        Customer customer = new Customer();
        customer.setNickname("c123");
        customer.setName("Cliente");
        customer.setMail("cliente@mail.com");
        customer.setSurname("Perez");
        customer.setCitizenship("Uruguay");
        customer.setBirthDate(LocalDate.of(1995, 1, 1));
        customer.setId("12345678");
        customer.setIdType(EnumTipoDocumento.CI);

        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customer);

        userService.registerCustomer(customerDTO);

        FlightRouteDTO routeDTO = new FlightRouteDTO();
        routeDTO.setName("Ruta X");
        routeDTO.setDescription("desc");
        routeDTO.setCreatedAt(LocalDate.now());
        routeDTO.setPriceTouristClass(90.0);
        routeDTO.setPriceBusinessClass(180.0);
        routeDTO.setPriceExtraUnitBaggage(25.0);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.addFlightRouteToAirline("c123", routeDTO);
        });

        assertTrue(ex.getMessage().contains("Airline no encontrada"));
    }
    @Test
    void getAirlineByNickname_shouldReturnEntity() {
        AirlineDTO dto = new AirlineDTO();
        dto.setNickname("vuela");
        dto.setName("Vuela");
        dto.setMail("vuela@mail.com");
        dto.setDescription("Super Airline");
        dto.setWeb("web.com");

        Airline entity = new Airline();
        entity.setNickname("vuela");
        entity.setName("Vuela");
        entity.setMail("vuela@mail.com");
        entity.setDescription("Super Airline");
        entity.setWeb("web.com");
        entity.setFlights(new ArrayList<>());
        entity.setFlightRoutes(new ArrayList<>());

        when(modelMapper.map(dto, Airline.class)).thenReturn(entity);

        userService.registerAirline(dto);

        Airline result = userService.getAirlineByNickname("vuela");

        // ✅ Esto está comparando el nickname, como debe ser
        assertEquals("vuela", result.getNickname());
    }

    @Test
    void getAirlineDetailsByNickname_shouldReturnDTO() {
        AirlineDTO dto = new AirlineDTO();
        dto.setNickname("express");
        dto.setName("Express");
        dto.setMail("express@mail.com");
        dto.setDescription("Compañía aérea rápida");
        dto.setWeb("express.com");

        Airline entity = new Airline();
        entity.setNickname("express");
        entity.setName("Express");
        entity.setMail("express@mail.com");
        entity.setDescription("Compañía aérea rápida"); // ✅
        entity.setWeb("express.com");
        entity.setFlights(new ArrayList<>());
        entity.setFlightRoutes(new ArrayList<>());

        when(modelMapper.map(dto, Airline.class)).thenReturn(entity);
        when(userMapper.toAirlineDTO(entity)).thenReturn(dto);

        userService.registerAirline(dto);
        AirlineDTO result = userService.getAirlineDetailsByNickname("express");

        assertNotNull(result);
        assertEquals("express", result.getNickname());
        assertEquals("Express", result.getName());
        assertEquals("Compañía aérea rápida", result.getDescription());
        assertEquals("express.com", result.getWeb());
    }


}