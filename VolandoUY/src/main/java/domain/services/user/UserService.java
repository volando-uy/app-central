package domain.services.user;

import domain.dtos.user.*;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import factory.ControllerFactory;
import infra.repository.user.UserRepository;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }



    @Override
    public List<AirlineDTO> getAllAirlinesDetails(boolean full) {
        List<Airline> airlines = full ? userRepository.getFullAllAirlines() : userRepository.getAllAirlines();

        return airlines.stream()
                .map(ar -> full ? customModelMapper.mapFullAirline(ar) : customModelMapper.map(ar, AirlineDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getAllCustomersDetails(boolean full) {
        List<Customer> customers = full ? userRepository.getFullAllCustomers() : userRepository.getAllCustomers();

        return customers.stream()
                .map(cu -> full ? customModelMapper.mapFullCustomer(cu) : customModelMapper.map(cu, CustomerDTO.class))
                .collect(Collectors.toList());
    }


    // REGISTRO DE CUSTOMER
    @Override
    public BaseCustomerDTO registerCustomer(BaseCustomerDTO customerDTO) {
        // Comprobamos que el usuario no exista
        if (existsUserByNickname(customerDTO.getNickname()) || _emailExists(customerDTO.getMail())) {
            throw new UnsupportedOperationException(ErrorMessages.ERR_USER_EXISTS);
        }

        // Creamos el nuevo customer
        Customer customer = customModelMapper.map(customerDTO, Customer.class);
        customer.setBoughtPackages(new ArrayList<>());
        customer.setBookedFlights(new ArrayList<>());

        // Lo validamos
        ValidatorUtil.validate(customer);

        // Lo guardamos en el repository
        userRepository.save(customer);

        return customModelMapper.mapBaseCustomer(customer);
    }

    // REGISTRO DE AEROLINEA
    @Override
    public BaseAirlineDTO registerAirline(BaseAirlineDTO airlineDTO) {
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

        return customModelMapper.mapBaseAirline(airline);
    }

    @Override
    public List<String> getAllUsersNicknames() {
        return userRepository.findAll().stream()
                .map(User::getNickname)
                .toList();
    }

    @Override
    public List<UserDTO> getAllUsers(boolean full) {
        List<Customer> customers = full ? userRepository.getFullAllCustomers() : userRepository.getAllCustomers();
        List<Airline> airlines = full ? userRepository.getFullAllAirlines() : userRepository.getAllAirlines();

        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(customers);
        allUsers.addAll(airlines);

        return allUsers.stream()
                .map(us -> full ? customModelMapper.mapFullUser(us) : customModelMapper.mapUser(us))
                .toList();
    }

    @Override
    public UserDTO getUserDetailsByNickname(String nickname, boolean full) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname.toLowerCase().trim(), false);
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }
        return full ? customModelMapper.mapFullUser(user) : customModelMapper.mapUser(user);
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO updatedUserDTO) {
        // 1) Buscar usuario
        User user = userRepository.getUserByNickname(nickname, false);
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }

        // 2) Aplicar cambios al usuario existente (mantiene el resto de campos)
        user.updateDataFrom(updatedUserDTO);

        // 3) Validar el estado completo tras el merge (ya no faltan campos)
        ValidatorUtil.validate(user);

        // 4) Persistir
        userRepository.update(user);

        // 5) Devolver DTO del tipo correcto
        return customModelMapper.mapUser(user);
    }

    @Override
    public Airline getAirlineByNickname(String nickname, boolean full) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname, full);

        // Checkeamos que el usuario sea una aerolinea
        if (user instanceof Airline airline) {
            return airline;
        } else {
            // Devolvemos error si no es una aerolinea
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, nickname));
        }

    }

    @Override
    public Customer getCustomerByNickname(String nickname, boolean full) {
        //  Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname, full);

        // Checkeamos que el usuario sea un customer
        if (user instanceof Customer customer) {
            return customer;
        } else {
            // Devolvemos error si no es un customer
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CUSTOMER_NOT_FOUND, nickname));
        }
    }


    @Override
    public CustomerDTO getCustomerDetailsByNickname(String nickname, boolean full) {
        // Comprobamos que el usuario exista
        User user = userRepository.getUserByNickname(nickname, full);

        // Checkeamos que el usuario sea un customer
        if (user instanceof Customer customer) {
            return customModelMapper.mapFullCustomer(customer);
        } else {
            // Devolvemos error si no es un customer
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CUSTOMER_NOT_FOUND, nickname));
        }
    }

    @Override
    public AirlineDTO getAirlineDetailsByNickname(String nickname, boolean full) {
        if (full) {
            return customModelMapper.mapFullAirline(getAirlineByNickname(nickname, true));
        } else {
            return customModelMapper.map(getAirlineByNickname(nickname, false), AirlineDTO.class);
        }
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

