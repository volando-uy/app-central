package domain.services.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;

import java.util.List;

//Aca no entran los DTO, porque se trabaja directamente con el dominio
public interface IUserService {
    CustomerDTO registerCustomer(CustomerDTO customerDTO);
    AirlineDTO registerAirline(AirlineDTO airlineDTO);
    List<UserDTO> getAllUsers();
    List<String> getAllUsersNicknames();
    UserDTO getUserByNickname(String nickname);
    UserDTO updateUser(String nickname, UserDTO userDTO);
    List<AirlineDTO> getAllAirlines();
    void addFlightRouteToAirline(Airline airline, FlightRoute flightRoute);
    Airline getAirlineByNickname(String nickname);
    AirlineDTO getAirlineDetailsByNickname(String nickname);
}
