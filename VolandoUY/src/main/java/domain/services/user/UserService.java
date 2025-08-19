package domain.services.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import org.modelmapper.ModelMapper;
import domain.models.user.mapper.UserMapper;
import shared.constants.ErrorMessages;

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
        users.add(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public AirlineDTO registerAirline(AirlineDTO airlineDTO) {
        Airline airline = modelMapper.map(airlineDTO, Airline.class);
        if (_userExists(airline)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_USUARIO_YA_EXISTE, airline.getNickname()));
        }
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
    public UserDTO updateTempUser(UserDTO newDataDTO) {
        // TODO: Pasarle esta responsabilidad al userRepository
        // Comprobar que estamos modificando un usuario existente
        User originalUser = _getUserByNickname(newDataDTO.getNickname());
        if (originalUser == null) {
            throw new IllegalArgumentException("User no encontrado: " + newDataDTO.getNickname());
        }

        // Si no es la primera modificación ya existirá un usuario temporal
        User userTemporal = usuariosTemporales.get(newDataDTO.getNickname());
        // En cambio, si es la primera vez, creamos uno nuevo
        if (userTemporal == null) {
            userTemporal = modelMapper.map(newDataDTO, originalUser.getClass());
        } else {
            // Si ya existe, lo actualizamos con los nuevos datos
            userTemporal.updateDataFrom(newDataDTO);
        }

        // Guardamos/Actualizamos el usuario temporal en el mapa
        usuariosTemporales.put(newDataDTO.getNickname(), userTemporal);

        // Retornamos el DTO del usuario temporal actualizado
        return userMapper.toDTO(userTemporal);
    }

    @Override
    public void updateUser(String nickname, UserDTO usuarioTempDTO) {
        User originalUser = _getUserByNickname(nickname);
        if (originalUser == null) return;

        originalUser.updateDataFrom(usuarioTempDTO);

        usuariosTemporales.remove(nickname);
    }





}
