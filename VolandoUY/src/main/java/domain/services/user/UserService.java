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
import infra.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import domain.models.user.mapper.UserMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.userRepository = new UserRepository();
    }



    @Override
    public List<AirlineDTO> getAllAirlinesDetails() {
        return userRepository.getAllAirlines().stream()
                .map(userMapper::toAirlineDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getAllCustomersDetails() {
        return userRepository.getAllCustomers().stream()
                .map(userMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }


    // REGISTRO DE CUSTOMER
    @Override
    public CustomerDTO registerCustomer(CustomerDTO dto) {
        if (_nicknameExists(dto.getNickname()) || _emailExists(dto.getMail())) {
            throw new UnsupportedOperationException(ErrorMessages.ERR_USER_EXISTS);
        }

        Customer customer = modelMapper.map(dto, Customer.class);
        ValidatorUtil.validate(customer);
        userRepository.save(customer);

        return modelMapper.map(
                userRepository.getUserByNickname(customer.getNickname()), CustomerDTO.class
        );
    }

    // REGISTRO DE AEROLINEA
    @Override
    public AirlineDTO registerAirline(AirlineDTO dto) {
        if (_nicknameExists(dto.getNickname()) || _emailExists(dto.getMail())) {
            throw new UnsupportedOperationException(ErrorMessages.ERR_USER_EXISTS);
        }

        Airline airline = modelMapper.map(dto, Airline.class);
        airline.setFlightRoutes(new ArrayList<>());
        airline.setFlights(new ArrayList<>());

        ValidatorUtil.validate(airline);
        userRepository.save(airline);

        return modelMapper.map(
                userRepository.getUserByNickname(airline.getNickname()), AirlineDTO.class
        );
    }

    @Override
    public List<String> getAllUsersNicknames() {
        return userRepository.findAll().stream()
                .map(User::getNickname)
                .toList();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserByNickname(String nickname) {
        User user = userRepository.getUserByNickname(nickname.toLowerCase().trim());
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO updatedUserDTO) {
        User user = userRepository.getUserByNickname(nickname);
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }

        User tempUser = userMapper.fromDTO(updatedUserDTO);
        ValidatorUtil.validate(tempUser);

        user.updateDataFrom(updatedUserDTO);

        // Guardar el update
        userRepository.update(user);

        return userMapper.toDTO(user);
    }

    @Override
    public Airline getAirlineByNickname(String nickname) {
        User user = userRepository.getUserByNickname(nickname);
        if (user instanceof Airline airline) {
            return airline;
        }
        throw new IllegalArgumentException("No se encontró una aerolínea con ese nickname.");
    }

    @Override
    public List<AirlineDTO> getAllAirlinesDetails() {
        List<Airline> airlines = airlineRepo.findAll();
        return airlines.stream()
                .map(userMapper::toAirlineDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getAllCustomersDetails() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(userMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerDetailsByNickname(String nickname) {
        User user = userRepository.getUserByNickname(nickname);
        if (user instanceof Customer customer) {
            return userMapper.toCustomerDTO(customer);
        }
        throw new IllegalArgumentException("No se encontró un cliente con ese nickname.");

    }

    @Override
    public AirlineDTO getAirlineDetailsByNickname(String nickname) {
        return userMapper.toAirlineDTO(getAirlineByNickname(nickname));
    }

    @Override
    public void addFlightRouteToAirline(Airline airline, FlightRoute flightRoute) {
        airline.getFlightRoutes().add(flightRoute);
        userRepository.save(airline);
    }

    public CustomerDTO getCustomerDetailsByNickname(String nickname) {
        Customer customer = (Customer) _getUserByNickname(nickname);
        if (customer == null) {
            throw new IllegalArgumentException("Customer no encontrado: " + nickname);
        }
        return userMapper.toCustomerDTO(customer);
    }

    // Función privada para obtener un usuario por nickname
    // No tira excepción si no lo encuentra, devuelve null
    private User _getUserByNickname(String nickname) {
        // Lo buscamos en el repo de clientes
        User user = customerRepo.findByKey(nickname);

        // Si no está, lo buscamos en el repo de aerolíneas
        if (user == null) {
            user = airlineRepo.findByKey(nickname);
        }
      
        return user;
    }


    // Métodos privados auxiliares
    private boolean _nicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    private boolean _emailExists(String email) {
        return userRepository.existsByEmail(email);
    }




}

