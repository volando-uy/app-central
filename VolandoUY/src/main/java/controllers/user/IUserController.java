package controllers.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;

import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUserController {
    CustomerDTO registerCustomer(CustomerDTO dto);
    AirlineDTO registerAirline(AirlineDTO dto);
    List<UserDTO> getAllUsers();
    List<AirlineDTO> getAllAirlines();
    FlightRouteDTO addFlightRouteToAirline(String airlineName, FlightRouteDTO flightRouteDTO);
    UserDTO getUserByNickname(String nickname);
    List<String> getAllUsersNicknames();
    UserDTO updateUser(String nickname, UserDTO user);
}
