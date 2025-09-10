package domain.services.user;

import domain.dtos.user.*;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.services.flightRoutePackage.IFlightRoutePackageService;

import java.util.List;

//Aca no entran los DTO, porque se trabaja directamente con el dominio
public interface IUserService {
    BaseCustomerDTO registerCustomer(BaseCustomerDTO customerDTO);
    BaseAirlineDTO registerAirline(BaseAirlineDTO airlineDTO);

    List<UserDTO> getAllUsers(boolean full);
    List<String> getAllUsersNicknames();

    List<AirlineDTO> getAllAirlinesDetails(boolean full);
    List<CustomerDTO> getAllCustomersDetails(boolean full);

    AirlineDTO getAirlineDetailsByNickname(String nickname, boolean full);
    CustomerDTO getCustomerDetailsByNickname(String nickname, boolean full);

    UserDTO getUserDetailsByNickname(String nickname, boolean full);
    UserDTO updateUser(String nickname, UserDTO userDTO);
    void addFlightRouteToAirline(Airline airline, FlightRoute flightRoute);

    Airline getAirlineByNickname(String nickname, boolean full);
    Customer getCustomerByNickname(String nickname, boolean full);

    void addFlightRoutePackageToCustomer(String customerNickname, String packageName);
    boolean existsUserByNickname(String nickname);

    void updateAirline(Airline airline);

    void setFlightRoutePackageService(IFlightRoutePackageService flightRoutePackageService);
}
