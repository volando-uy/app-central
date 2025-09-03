package domain.services.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import factory.ControllerFactory;
import infra.repository.BaseRepository;
import infra.repository.user.AirlineRepository;
import infra.repository.user.CustomerRepository;
import infra.repository.user.UserRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import domain.models.user.mapper.UserMapper;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    private final UserRepository userRepository;

    @Setter
    private IFlightRoutePackageService flightRoutePackageService;

    public UserService() {
        this.userRepository = new UserRepository();
    }



    @Override
    public List<AirlineDTO> getAllAirlinesDetails() {
        return userRepository.getAllAirlines().stream()
                .map(customModelMapper::mapAirline)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getAllCustomersDetails() {
        return userRepository.getAllCustomers().stream()
                .map(customModelMapper::mapCustomer)
                .collect(Collectors.toList());
    }


    // REGISTRO DE CUSTOMER
    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        // Comprobamos que el usuario no exista
        if (existsUserByNickname(customerDTO.getNickname()) || _emailExists(customerDTO.getMail())) {
            throw new UnsupportedOperationException(ErrorMessages.ERR_USER_EXISTS);
        }

        // Creamos el nuevo customer
        Customer customer = customModelMapper.map(customerDTO, Customer.class);

        // Lo validamos
        ValidatorUtil.validate(customer);

        // Lo guardamos en el repository
        userRepository.save(customer);

        return customModelMapper.mapCustomer(customer);
    }

    // REGISTRO DE AEROLINEA
    @Override
    public AirlineDTO registerAirline(AirlineDTO airlineDTO) {
        // Comprobamos que el usuario no exista
        if (existsUserByNickname(airlineDTO.getNickname()) || _emailExists(airlineDTO.getMail())) {
            throw new UnsupportedOperationException(ErrorMessages.ERR_USER_EXISTS);
        }

        // Mapeamos el DTO a entidad
        Airline airline = customModelMapper.map(airlineDTO, Airline.class);
        airline.setFlightRoutes(new ArrayList<>());
        airline.setFlights(new ArrayList<>());

        // Validamos la entidad
        ValidatorUtil.validate(airline);

        // Guardamos la nueva aerolinea
        userRepository.save(airline);

        return customModelMapper.mapAirline(airline);
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
                .map(customModelMapper::mapUser)
                .toList();
    }

    @Override
    public UserDTO getUserByNickname(String nickname) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname.toLowerCase().trim());
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }
        return customModelMapper.mapUser(user);
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO updatedUserDTO) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname);
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }

        // Creamos un usuario temporal para validar los datos nuevos
        User tempUser = customModelMapper.mapUserDTO(updatedUserDTO);

        // Validamos los datos
        ValidatorUtil.validate(tempUser);

        // Actualizamos los datos del usuario original
        user.updateDataFrom(updatedUserDTO);

        // Guardar el update
        userRepository.update(user);

        return customModelMapper.mapUser(user);
    }

    @Override
    public Airline getAirlineByNickname(String nickname) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname);

        // Checkeamos que el usuario sea una aerolinea
        if (user instanceof Airline airline) {
            return airline;
        } else {
            // Devolvemos error si no es una aerolinea
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, nickname));
        }

    }

    @Override
    public Customer getCustomerByNickname(String nickname) {
        //  Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname);

        // Checkeamos que el usuario sea un customer
        if (user instanceof Customer customer) {
            return customer;
        } else {
            // Devolvemos error si no es un customer
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CUSTOMER_NOT_FOUND, nickname));
        }
    }


    @Override
    public CustomerDTO getCustomerDetailsByNickname(String nickname) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname);

        // Checkeamos que el usuario sea un customer
        if (user instanceof Customer customer) {
            return customModelMapper.mapCustomer(customer);
        } else {
            // Devolvemos error si no es un customer
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CUSTOMER_NOT_FOUND, nickname));
        }
    }

    @Override
    public AirlineDTO getAirlineDetailsByNickname(String nickname) {
        return customModelMapper.mapAirline(getAirlineByNickname(nickname));
    }

    @Override
    public void addFlightRouteToAirline(Airline airline, FlightRoute flightRoute) {
        airline.getFlightRoutes().add(flightRoute);
        userRepository.save(airline);
    }

    @Override
    public void addFlightRoutePackageToCustomer(String customerNickname, String packageName) {
//        Customer customer = this.getCustomerByNickname(customerNickname);
//
//        // Buscar el paquete por su nombre
//        // Tira throw si no lo encuentra
//        FlightRoutePackage flightRoutePackage = flightRoutePackageService.getFlightRoutePackageByName(packageName);
//
//        // Verificar si ya compró este paquete
//        customer.getBoughtPackages().stream()
//                .filter(pkg -> pkg.getFlightRoutePackage().getName().equalsIgnoreCase(packageName))
//                .findFirst()
//                .ifPresent(pkg -> {
//                    throw new IllegalArgumentException(
//                            String.format("El cliente %s ya compró el paquete %s", customerNickname, packageName)
//                    );
//                });
//
//        // Validar que el paquete no esté vencido
//        if (flightRoutePackage.isExpired()) {
//            throw new IllegalArgumentException(
//                    String.format("El paquete %s ya expiró y no puede ser comprado", packageName)
//            );
//        }
//
//        // Creamos el buy package
//        BuyPackage buyPackage = new BuyPackage();
//        buyPackage.setCustomer(customer);
//        buyPackage.setFlightRoutePackage(flightRoutePackage);
//        buyPackage.setCreatedAt(LocalDateTime.now());
//        buyPackage.setTotalPrice(flightRoutePackage.getTotalPrice());
//
//        // Añadimos el buy package al customer
//        customer.getBoughtPackages().add(buyPackage);
//
//        // Añadimos el buy package al flight route package
//        flightRoutePackage.getBuyPackages().add(buyPackage);
//
//        // Guardar cambios en ambos repositorios
//        userRepository.save(customer);
//        // buyPackageService.save(buyPackage);
//        flightRoutePackageService._updateFlightRoutePackage(flightRoutePackage);
    }


    private boolean _emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsUserByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public void updateAirline(Airline airline) {
        userRepository.update(airline);
    }
}

