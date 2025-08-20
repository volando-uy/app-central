package controllers.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.services.user.IUserService;
import lombok.AllArgsConstructor;
import shared.utils.AnnotationValidator;

import java.util.List;

@AllArgsConstructor
public class UserController implements IUserController {
    IUserService userService;

    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        AnnotationValidator.validateRequiredFields(customerDTO);
        return userService.registerCustomer(customerDTO);
    }

    @Override
    public AirlineDTO registerAirline(AirlineDTO airlineDTO) {
        AnnotationValidator.validateRequiredFields(airlineDTO);
        return userService.registerAirline(airlineDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public List<String> getAllUsersNicknames() {
        return userService.getAllUsersNicknames();
    }

    @Override
    public UserDTO getUserByNickname(String nickname) {
        UserDTO userDTO = userService.getUserByNickname(nickname);
        return userDTO;
    }

    @Override
    public void updateUser(String nickname, UserDTO user) {
        AnnotationValidator.validateRequiredFields(user);
        userService.updateUser(nickname, user);
    }

    @Override
    public UserDTO updateTemporalUser(UserDTO user) {
        AnnotationValidator.validateRequiredFields(user);
        UserDTO modifiedUser = userService.updateTempUser(user);
        return modifiedUser;
    }

    @Override
    public List<AirlineDTO> getAllAirlines() {
        return userService.getAllAirlines();
    }

    @Override
    public FlightRouteDTO addFlightRouteToAirline(String airlineName, FlightRouteDTO flightRouteDTO) {
        return userService.addFlightRouteToAirline(airlineName, flightRouteDTO);
    }
}
