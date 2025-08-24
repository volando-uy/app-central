package domain.services.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import infra.repository.BaseRepository;
import infra.repository.user.AirlineRepository;
import infra.repository.user.CustomerRepository;
import org.modelmapper.ModelMapper;
import domain.models.user.mapper.UserMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class UserService implements IUserService {
    private ModelMapper modelMapper;
    private UserMapper userMapper;
    CustomerRepository customerRepo;
    AirlineRepository airlineRepo;

    // ############### Constructor ###############
    public UserService(ModelMapper modelMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.customerRepo = new CustomerRepository();
        this.airlineRepo = new AirlineRepository();
    }


    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {

        // Convertimos el DTO a entidad y verificamos si ya existe
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        if (_nicknameExists(customerDTO.getNickname()) || _emailExists(customerDTO.getMail())) {
            throw new UnsupportedOperationException((ErrorMessages.ERR_USER_EXISTS));
        }

        // Validamos el cliente
        ValidatorUtil.validate(customer);

        // Guardamos el cliente en la base de datos usando el repositorio
        customerRepo.save(customer);

        // Comprobamos que se guardó correctamente
        Customer savedCustomer = customerRepo.findByKey(customer.getNickname());
        if (savedCustomer == null) {
            throw new RuntimeException("Error al guardar el cliente en la base de datos.");
        }

        // Devolvemos el cliente guardado como DTO
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }



    @Override
    public AirlineDTO registerAirline(AirlineDTO airlineDTO) {

        // Convertimos el DTO a entidad y verificamos si ya existe
        Airline airline = modelMapper.map(airlineDTO, Airline.class);
        if (_nicknameExists(airline.getNickname()) || _emailExists(airline.getMail())) {
            throw new UnsupportedOperationException((ErrorMessages.ERR_USER_EXISTS));
        }

        // Validamos la aerolínea
        ValidatorUtil.validate(airline);

        // Setteamos listas vacias a los vuelos y rutas de vuelo.
        airline.setFlightRoutes(new ArrayList<>());
        airline.setFlights(new ArrayList<>());

        // Guardamos la aerolínea en la base de datos usando el repositorio
        airlineRepo.save(airline);

        // Comprobamos que se guardó correctamente
        Airline savedAirline = airlineRepo.findByKey(airline.getNickname());
        if (savedAirline == null) {
            throw new RuntimeException("Error al guardar la aerolínea en la base de datos.");
        }

        // Devolvemos la aerolínea guardada como DTO
        return modelMapper.map(airline, AirlineDTO.class);
    }

    @Override
    public List<String> getAllUsersNicknames() {
        // Inicializamos la lista de nicknames
        List<String> nicknames = new ArrayList<>();

        // Obtenemos todos los usuarios de ambos repositorios
        List<Customer> customers = customerRepo.findAll();
        List<Airline> airlines = airlineRepo.findAll();

        // Agregamos los nicknames de los clientes
        for (Customer customer : customers) {
            nicknames.add(customer.getNickname());
        }

        // Agregamos los nicknames de las aerolíneas
        for (Airline airline : airlines) {
            nicknames.add(airline.getNickname());
        }

        // Devolvemos la lista completa de nicknames
        return nicknames;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // Inicializamos la lista de UserDTO
        List<UserDTO> userDTOs = new ArrayList<>();

        // Obtenemos todos los usuarios de ambos repositorios
        List<Customer> customers = customerRepo.findAll();
        List<Airline> airlines = airlineRepo.findAll();

        // Convertimos y agregamos los clientes a la lista de UserDTO
        for (Customer customer : customers) {
            userDTOs.add(userMapper.toDTO(customer));
        }

        // Convertimos y agregamos las aerolíneas a la lista de UserDTO
        for (Airline airline : airlines) {
            userDTOs.add(userMapper.toDTO(airline));
        }

        // Antes de devolver, asegurarse de agregar atributos Objetos al DTO en caso de que tenga
        return userDTOs;
    }


    public UserDTO getUserByNickname(String nickname) {
        // Lo buscamos en el repo de clientes
        String nicknameLowerCase = nickname.trim().toLowerCase();
        User user = _getUserByNickname(nicknameLowerCase);
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }

        // Convertimos a DTO y lo devolvemos
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO updatedUserDTO) {
        // Buscamos el usuario original por nickname
        User originalUser = _getUserByNickname(nickname);
        if (originalUser == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }

        // Validamos si los datos nuevos son correctos
        User tempUser = userMapper.fromDTO(updatedUserDTO);
        ValidatorUtil.validate(tempUser);

        // Actualizamos los datos del usuario original con los del DTO
        originalUser.updateDataFrom(updatedUserDTO);

        // Guardamos los cambios en la base de datos usando el repositorio correspondiente
        User updatedUser;
        if (originalUser instanceof Customer) {
            updatedUser = customerRepo.update((Customer) originalUser);
        } else if (originalUser instanceof Airline) {
            updatedUser = airlineRepo.update((Airline) originalUser);
        } else {
            throw new IllegalStateException("Tipo de usuario desconocido.");
        }

        return userMapper.toDTO(updatedUser);
    }



    @Override
    public List<AirlineDTO> getAllAirlines() {
        List<Airline> airlines = airlineRepo.findAll();
        return airlines.stream()
                .map(userMapper::toAirlineDTO)
                .collect(Collectors.toList());
    }

    @Override
    // Esta función agrega una ruta de vuelo a una aerolínea
    // Se utiliza al crear una nueva ruta de vuelo desde el controlador de rutas de vuelo
    public void addFlightRouteToAirline(Airline airline, FlightRoute flightRoute) {

        // Asegurar que la lista esté inicializada
        if (airline.getFlightRoutes() == null) {
            airline.setFlightRoutes(new ArrayList<>());
        }

        // Agregar la nueva ruta aérea a la aerolínea y viceversa
        airline.getFlightRoutes().add(flightRoute);

        // Guardar los cambios en la base de datos
        airlineRepo.update(airline);
    }


    @Override
    public Airline getAirlineByNickname(String nickname) {
        Airline airline = (Airline) _getUserByNickname(nickname);
        if (airline == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }
        return airline;
    }

    @Override
    public AirlineDTO getAirlineDetailsByNickname(String nickname) {
        Airline airline = (Airline) _getUserByNickname(nickname);
        if (airline == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }
        return userMapper.toAirlineDTO(airline);
    }

    // Función privada para obtener un usuario por nickname
    // No tira excepción si no lo encuentra, devuelve null
    private User _getUserByNickname(String nickname) {
        // Lo buscamos en el repo de clientes

        User user = customerRepo.getCustomerByNickname(nickname);

        // Si no está, lo buscamos en el repo de aerolíneas
        if (user == null) {
            user = airlineRepo.getAirlineByNickname(nickname);
        }

        return user;
    }

    // Funciones para preguntar si existe un usuario Aereolinea O Cliente

    private boolean _nicknameExists(String nickname) {
        return customerRepo.existsByNickname(nickname) ||  airlineRepo.existsByNickname(nickname);
    }

    private boolean _emailExists(String email) {
        return customerRepo.existsByEmail(email) || airlineRepo.existsByEmail(email);
    }




}
