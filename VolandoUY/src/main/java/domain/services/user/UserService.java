package domain.services.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import org.modelmapper.ModelMapper;
import domain.models.user.mapper.UserMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class UserService implements IUserService {
    //Supongamos que tenemos el UserRepository
    private LinkedList<User> users = new LinkedList<>();
    private Map<String, User> usuariosTemporales = new HashMap<>();
    private ModelMapper modelMapper;
    private UserMapper userMapper;

    public UserService(ModelMapper modelMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
    }


    // Les puse _ a las funciones internas, que luego van a ser parte del repository.
    private User _getUserByNickname(String nickname){
        return this.users.stream()
                .filter(u -> u.getNickname().equalsIgnoreCase(nickname))
                .findFirst()
                .orElse(null);
    }

    // Funcion para saber si existe un user con el mismo nickname
    private boolean _userExists(User user) {
        // TODO: usar acá el userRepository
        return users.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(user.getNickname()));
    }


    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);

        if (_userExists(customer)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_USUARIO_YA_EXISTE, customer.getNickname()));
        }

        ValidatorUtil.validate(customer);

        users.add(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public AirlineDTO registerAirline(AirlineDTO airlineDTO) {
        Airline airline = modelMapper.map(airlineDTO, Airline.class);
        if (_userExists(airline)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_USUARIO_YA_EXISTE, airline.getNickname()));
        }

        ValidatorUtil.validate(airline);

        users.add(airline);
        return modelMapper.map(airline, AirlineDTO.class);
    }

    @Override
    public List<String> getAllUsersNicknames() {
        // TODO: Pasar esta responsabilidad al userRepository
        return users.stream().map(User::getNickname).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // TODO: Pasar esta responsabilidad al userRepository
        List<UserDTO> userDTOs = this.users.stream()
                .map(usuario -> userMapper.toDTO(usuario))
                .collect(Collectors.toList());

        // Antes de devolver, asegurarse de agregar atributos Objetos al DTO en caso de que tenga
        return userDTOs;
    }


    public UserDTO getUserByNickname(String nickname) {
        User user = _getUserByNickname(nickname);
        if (user == null) {
            throw new IllegalArgumentException("User no encontrado: " + nickname);
        }
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO updatedUserDTO) {
        User originalUser = _getUserByNickname(nickname);
        if (originalUser == null) {
            throw new IllegalArgumentException("User no encontrado: " + nickname);
        };

        User tempUser = userMapper.fromDTO(updatedUserDTO);
        ValidatorUtil.validate(tempUser);

        originalUser.updateDataFrom(updatedUserDTO);
        return userMapper.toDTO(originalUser);
    }

    @Override
    public List<AirlineDTO> getAllAirlines(){
        // Filtramos las aerolíneas de la lista de usuarios
        List<AirlineDTO> airlines = users.stream()
                .filter(user -> user instanceof Airline)
                .map(user -> modelMapper.map(user, AirlineDTO.class))
                .toList();
        return airlines;
    }

    @Override
    public FlightRouteDTO addFlightRouteToAirline(String airlineName, FlightRouteDTO flightRouteDTO) {
        User user = _getUserByNickname(airlineName);
        if (user == null || !(user instanceof Airline)) {
            throw new IllegalArgumentException("Airline no encontrada: " + airlineName);
        }

        Airline airline = modelMapper.map(airlineName, Airline.class);

        // Convertir FlightRouteDTO a FlightRoute
        FlightRoute flightRoute = modelMapper.map(flightRouteDTO, FlightRoute.class);

        // Agregar la nueva ruta aérea a la aerolínea
        airline.getFlightRoutes().add(flightRoute);

        // Convertir de nuevo a DTO para devolver
        return modelMapper.map(flightRoute, FlightRouteDTO.class);
    }
}
