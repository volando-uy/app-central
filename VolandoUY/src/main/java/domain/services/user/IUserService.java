package domain.services.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.services.flightRoutePackage.IFlightRoutePackageService;

import java.util.List;

//Aca no entran los DTO, porque se trabaja directamente con el dominio
public interface IUserService {
    CustomerDTO registerCustomer(CustomerDTO customerDTO);
    AirlineDTO registerAirline(AirlineDTO airlineDTO);

    List<UserDTO> getAllUsers();
    List<String> getAllUsersNicknames();

    List<CustomerDTO> getAllCustomersDetails();
    List<AirlineDTO> getAllAirlinesDetails();

    AirlineDTO getAirlineDetailsByNickname(String nickname);
    CustomerDTO getCustomerDetailsByNickname(String nickname);

    UserDTO getUserByNickname(String nickname);
    UserDTO updateUser(String nickname, UserDTO userDTO);
    void addFlightRouteToAirline(Airline airline, FlightRoute flightRoute);

    Airline getAirlineByNickname(String nickname);
    Customer getCustomerByNickname(String nickname);

    void addFlightRoutePackageToCustomer(String customerNickname, String packageName);

    boolean existsUserByNickname(String nickname);

    void setFlightRoutePackageService(IFlightRoutePackageService flightRoutePackageService);
}
