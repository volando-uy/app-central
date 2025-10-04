package domain.services.user;

import domain.dtos.user.*;
import domain.models.buypackage.BuyPackage;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import domain.services.buyPackage.IBuyPackageService;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.user.IUserRepository;
import infra.repository.user.UserRepository;
import lombok.Setter;
import shared.constants.ErrorMessages;
import shared.constants.Images;
import shared.utils.CustomModelMapper;
import shared.utils.ImageProcessor;
import shared.utils.PasswordManager;
import shared.utils.ValidatorUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    private final IUserRepository userRepository;

    @Setter
    private IFlightRoutePackageService flightRoutePackageService;

    @Setter
    private IBuyPackageService buyPackageService;

    public UserService() {
        this.userRepository = RepositoryFactory.getUserRepository();
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
    public BaseCustomerDTO registerCustomer(BaseCustomerDTO customerDTO, File imageFile) {
        // Comprobamos que el usuario no exista
        if (existsUserByNickname(customerDTO.getNickname()) || _emailExists(customerDTO.getMail())) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_USER_EXISTS, customerDTO.getNickname()));
        }

        // Creamos el nuevo customer
        Customer customer = customModelMapper.map(customerDTO, Customer.class);
        customer.setBoughtPackages(new ArrayList<>());
        customer.setBookedFlights(new ArrayList<>());

        // Lo validamos
        ValidatorUtil.validate(customer);

        if (imageFile != null) {
            String imagePath = Images.CUSTOMERS_PATH + customer.getNickname() + Images.FORMAT_DEFAULT;
            String uploadedImagePath = ImageProcessor.uploadImage(imageFile, imagePath);
            customer.setImage(uploadedImagePath);
        } else {
            customer.setImage(Images.USER_DEFAULT);
        }

        // Hash the password
        String hashedPassword = PasswordManager.hashPassword(customer.getPassword());
        customer.setPassword(hashedPassword);

        // Lo guardamos en el repository
        userRepository.save(customer);

        return customModelMapper.mapBaseCustomer(customer);
    }

    // REGISTRO DE AEROLINEA
    @Override
    public BaseAirlineDTO registerAirline(BaseAirlineDTO airlineDTO, File imageFile) {
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

        // Subir la imagen si tiene
        if (imageFile != null) {
            String imagePath = Images.AIRLINES_PATH + airline.getNickname() + Images.FORMAT_DEFAULT;
            String uploadedImagePath = ImageProcessor.uploadImage(imageFile, imagePath);
            airline.setImage(uploadedImagePath);
        } else {
            airline.setImage(Images.USER_DEFAULT);
        }

        // Hash the password
        String hashedPassword = PasswordManager.hashPassword(airline.getPassword());
        airline.setPassword(hashedPassword);

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
    public UserDTO updateUser(String nickname, UserDTO updatedUserDTO, File imageFile) {
        // 1) Buscar usuario
        User user = userRepository.getUserByNickname(nickname, false);
        if (user == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, nickname));
        }

        // 2) Aplicar cambios al usuario existente (mantiene el resto de campos)
        user.updateDataFrom(updatedUserDTO);

        // 3) Validar el estado completo tras el merge (ya no faltan campos)
        ValidatorUtil.validate(user);

        // Subir la imagen si tiene
        if (imageFile != null) {
            String imagePath;
            if (user instanceof Customer) {
                imagePath = Images.CUSTOMERS_PATH + user.getNickname() + Images.FORMAT_DEFAULT;
            } else if (user instanceof Airline) {
                imagePath = Images.AIRLINES_PATH + user.getNickname() + Images.FORMAT_DEFAULT;
            } else {
                throw new IllegalStateException("Error interno: tipo de usuario desconocido");
            }
            String uploadedImagePath = ImageProcessor.uploadImage(imageFile, imagePath);
            user.setImage(uploadedImagePath);
        }

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

