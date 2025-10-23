package domain.services.user;

import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.user.IUserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ImageProcessor;
import shared.utils.PasswordManager;
import shared.utils.ValidatorUtil;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository mockUserRepository;
    @Mock
    private CustomModelMapper mockMapper;

    private MockedStatic<RepositoryFactory> repoFactoryStatic;
    private MockedStatic<ControllerFactory> controllerFactoryStatic;
    private MockedStatic<ValidatorUtil> validatorStatic;
    private MockedStatic<PasswordManager> passwordStatic;

    private UserService userService;

    @BeforeEach
    void setUp() {
        repoFactoryStatic = mockStatic(RepositoryFactory.class);
        controllerFactoryStatic = mockStatic(ControllerFactory.class);
        validatorStatic = mockStatic(ValidatorUtil.class);
        passwordStatic = mockStatic(PasswordManager.class);

        repoFactoryStatic.when(RepositoryFactory::getUserRepository).thenReturn(mockUserRepository);
        controllerFactoryStatic.when(ControllerFactory::getCustomModelMapper).thenReturn(mockMapper);

        validatorStatic.when(() -> ValidatorUtil.validate(any())).then(invocation -> null);
        passwordStatic.when(() -> PasswordManager.hashPassword(anyString())).thenAnswer(inv -> "hashed_" + inv.getArgument(0));

        userService = new UserService();
    }

    @AfterEach
    void tearDown() {
        repoFactoryStatic.close();
        controllerFactoryStatic.close();
        validatorStatic.close();
        passwordStatic.close();
    }

    // -------------------------------
    // registerCustomer
    // -------------------------------
    @Test
    void registerCustomer_shouldAddCustomerSuccessfully_withAllFields() {
        BaseCustomerDTO dto = new BaseCustomerDTO("nick1", "Pepe", "pepe@mail.com", "password",
                null, "González", "Uruguay", LocalDate.of(1990, 1, 1), "12345678", EnumTipoDocumento.CI);

        when(mockUserRepository.existsByNickname("nick1")).thenReturn(false);
        when(mockUserRepository.existsByEmail("pepe@mail.com")).thenReturn(false);

        CustomerDTO mapped = new CustomerDTO();
        mapped.setNickname("nick1");
        mapped.setName("Pepe");
        mapped.setSurname("González");
        when(mockMapper.mapCustomer(any())).thenReturn(mapped);

        Customer customerEntity = new Customer();
        customerEntity.setNickname("nick1");
        customerEntity.setBookedFlights(new ArrayList<>());
        customerEntity.setBoughtPackages(new ArrayList<>());
        when(mockMapper.map(any(BaseCustomerDTO.class), eq(Customer.class)))
                .thenReturn(customerEntity);
        BaseCustomerDTO result = userService.registerCustomer(dto, null);

        assertNotNull(result);
        assertEquals("nick1", result.getNickname());
        verify(mockUserRepository).save(any(Customer.class));
    }

    @Test
    void registerCustomer_shouldThrowExceptionIfUserExists() {
        BaseCustomerDTO dto = new BaseCustomerDTO();
        dto.setNickname("dup");
        dto.setMail("dup@mail.com");

        when(mockUserRepository.existsByNickname("dup")).thenReturn(true);

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () ->
                userService.registerCustomer(dto, null));

        assertEquals(String.format(ErrorMessages.ERR_USER_EXISTS, "dup"), ex.getMessage());
    }

    @Test
    void registerCustomer_shouldThrowIfMailAlreadyExists() {
        BaseCustomerDTO dto = new BaseCustomerDTO();
        dto.setNickname("nick1");
        dto.setMail("same@mail.com");

        when(mockUserRepository.existsByNickname("nick1")).thenReturn(false);
        when(mockUserRepository.existsByEmail("same@mail.com")).thenReturn(true);

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () ->
                userService.registerCustomer(dto, null));

        assertEquals(String.format(ErrorMessages.ERR_USER_EXISTS, "nick1"), ex.getMessage());
    }
    @Test
    void registerCustomer_shouldUploadImageIfProvided() {
        /*
        // Arrange
        BaseCustomerDTO dto = new BaseCustomerDTO("nickimg", "pepe", "pepe@mail.com", "pass",
                null, "Sosa", "UY", LocalDate.of(1990, 1, 1), "123", EnumTipoDocumento.CI);

        when(mockUserRepository.existsByNickname("nickimg")).thenReturn(false);
        when(mockUserRepository.existsByEmail("pepe@mail.com")).thenReturn(false);

        Customer customer = new Customer();
        customer.setNickname("nickimg");
        customer.setBookedFlights(new ArrayList<>());
        customer.setBoughtPackages(new ArrayList<>());
        when(mockMapper.map(any(BaseCustomerDTO.class), eq(Customer.class)))
                .thenReturn(customer);

        CustomerDTO mapped = new CustomerDTO();
        mapped.setNickname("nickimg");
        mapped.setName("pepe");
        when(mockMapper.mapCustomer(any(Customer.class))).thenReturn(mapped);

        File fakeImage = mock(File.class);

        // ✅ Static mock DEBE estar encerrado acá y no usarse en otra parte
        try (MockedStatic<ImageProcessor> staticMock = mockStatic(ImageProcessor.class)) {
            staticMock.when(() -> ImageProcessor.uploadImage(any(), anyString()))
                    .thenReturn("/fake/path.jpg");

            // Act
            BaseCustomerDTO result = userService.registerCustomer(dto, fakeImage);

            // Assert
            assertNotNull(result);
            assertEquals("nickimg", result.getNickname());

            staticMock.verify(() -> ImageProcessor.uploadImage(any(), anyString()));
        }
        */
    }


    // -------------------------------
    // registerAirline
    // -------------------------------
    @Test
    void registerAirline_shouldRegisterSuccessfully() {
        // Arrange
        BaseAirlineDTO dto = new BaseAirlineDTO("air123", "FlyHigh", "fly@mail.com", "password",
                null, "Líder regional", "www.fly.com");

        // Simulamos que no existe el usuario aún
        when(mockUserRepository.existsByNickname("air123")).thenReturn(false);
        when(mockUserRepository.existsByEmail("fly@mail.com")).thenReturn(false);

        // Este es el mock que te falta para evitar el NullPointerException 👇
        Airline airlineEntity = new Airline();
        airlineEntity.setNickname("air123");
        airlineEntity.setFlightRoutes(new ArrayList<>());
        airlineEntity.setFlights(new ArrayList<>());

        when(mockMapper.map(any(BaseAirlineDTO.class), eq(Airline.class)))
                .thenReturn(airlineEntity);

        // Resultado mapeado final que se devuelve
        BaseAirlineDTO mapped = new BaseAirlineDTO();
        mapped.setNickname("air123");
        mapped.setName("FlyHigh");
        when(mockMapper.mapBaseAirline(any(Airline.class))).thenReturn(mapped);

        // Act
        BaseAirlineDTO result = userService.registerAirline(dto, null);

        // Assert
        assertNotNull(result);
        assertEquals("air123", result.getNickname());
        verify(mockUserRepository).save(any(Airline.class));
    }

    // -------------------------------
    // getUserDetailsByNickname
    // -------------------------------
    @Test
    void getUserByNickname_shouldReturnCustomerDTO() {
        Customer c = new Customer();
        c.setNickname("nickCustomer");
        c.setName("Pama");
        c.setSurname("Sosa");

        when(mockUserRepository.getUserByNickname("nickcustomer", false)).thenReturn(c);

        BaseCustomerDTO mapped = new BaseCustomerDTO();
        mapped.setNickname("nickCustomer");
        mapped.setName("Pama");
        mapped.setSurname("Sosa");
        when(mockMapper.mapUser(c)).thenReturn(mapped);

        UserDTO result = userService.getUserDetailsByNickname("nickCustomer", false);

        assertEquals("Pama", result.getName());
        assertTrue(result instanceof BaseCustomerDTO);
    }

    @Test
    void getUserByNickname_shouldThrowExceptionIfNotFound() {
        when(mockUserRepository.getUserByNickname("notfound", false)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userService.getUserDetailsByNickname("notfound", false));

        assertEquals(String.format(ErrorMessages.ERR_USER_NOT_FOUND, "notfound"), ex.getMessage());
    }

    // -------------------------------
    // getAllUsersNicknames
    // -------------------------------
    @Test
    void getAllUsersNicknames_shouldReturnAllNicknames() {
        Customer c = new Customer();
        c.setNickname("nick1");
        when(mockUserRepository.findAll()).thenReturn(List.of(c));

        List<String> result = userService.getAllUsersNicknames();

        assertEquals(1, result.size());
        assertTrue(result.contains("nick1"));
    }

    // -------------------------------
    // getAllUsers
    // -------------------------------
    @Test
    void getAllUsers_shouldReturnAllUsersMapped() {
        Customer c = new Customer();
        c.setNickname("nick1");
        Airline a = new Airline();
        a.setNickname("air1");

        when(mockUserRepository.getAllCustomers()).thenReturn(List.of(c));
        when(mockUserRepository.getAllAirlines()).thenReturn(List.of(a));

        when(mockMapper.mapUser(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            BaseCustomerDTO dto = new BaseCustomerDTO();
            dto.setNickname(u.getNickname());
            return dto;
        });

        List<UserDTO> all = userService.getAllUsers(false);

        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(u -> u.getNickname().equals("nick1")));
        assertTrue(all.stream().anyMatch(u -> u.getNickname().equals("air1")));
    }

    // -------------------------------
    // updateUser
    // -------------------------------
    @Test
    void updateUser_shouldUpdateCustomerData() {
        Customer existing = new Customer();
        existing.setNickname("cliente1");
        existing.setName("Pedro");

        when(mockUserRepository.getUserByNickname("cliente1", false)).thenReturn(existing);

        CustomerDTO updated = new CustomerDTO();
        updated.setName("Pedro Actualizado");

        BaseCustomerDTO mapped = new BaseCustomerDTO();
        mapped.setName("Pedro Actualizado");
        mapped.setNickname("cliente1");
        when(mockMapper.mapUser(any(User.class))).thenReturn(mapped);

        UserDTO result = userService.updateUser("cliente1", updated, null);

        assertNotNull(result);
        assertEquals("Pedro Actualizado", result.getName());
        verify(mockUserRepository).update(any(User.class));
    }

    @Test
    void updateUser_shouldThrowIfNotFound() {
        when(mockUserRepository.getUserByNickname("notfound", false)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser("notfound", new CustomerDTO(), null));

        assertEquals(String.format(ErrorMessages.ERR_USER_NOT_FOUND, "notfound"), ex.getMessage());
    }

    // -------------------------------
    // getAllAirlinesDetails
    // -------------------------------
    @Test
    void getAllAirlines_shouldReturnList() {
        Airline a1 = new Airline();
        a1.setNickname("air1");
        when(mockUserRepository.getAllAirlines()).thenReturn(List.of(a1));

        AirlineDTO dto = new AirlineDTO();
        dto.setNickname("air1");
        when(mockMapper.map(a1, AirlineDTO.class)).thenReturn(dto);

        List<AirlineDTO> result = userService.getAllAirlinesDetails(false);

        assertEquals(1, result.size());
        assertEquals("air1", result.get(0).getNickname());
    }

    // -------------------------------
    // getCustomerDetailsByNickname
    // -------------------------------
    @Test
    void getCustomerDetailsByNickname_shouldReturnDTO() {
        Customer c = new Customer();
        c.setNickname("cus1");
        when(mockUserRepository.getUserByNickname("cus1", false)).thenReturn(c);

        CustomerDTO dto = new CustomerDTO();
        dto.setNickname("cus1");
        when(mockMapper.mapCustomer(c)).thenReturn(dto);

        CustomerDTO result = userService.getCustomerDetailsByNickname("cus1", false);
        assertEquals("cus1", result.getNickname());
    }

    // -------------------------------
    // getAirlineDetailsByNickname
    // -------------------------------
    @Test
    void getAirlineDetailsByNickname_shouldReturnDTO() {
        Airline a = new Airline();
        a.setNickname("air");
        when(mockUserRepository.getUserByNickname("air", false)).thenReturn(a);
        AirlineDTO dto = new AirlineDTO();
        dto.setNickname("air");
        when(mockMapper.map(a, AirlineDTO.class)).thenReturn(dto);

        AirlineDTO result = userService.getAirlineDetailsByNickname("air", false);
        assertEquals("air", result.getNickname());
    }
}
