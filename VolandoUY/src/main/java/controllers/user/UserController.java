package controllers.user;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.services.user.IUserService;
import lombok.AllArgsConstructor;
import shared.utils.AnnotationValidator;
import shared.utils.ValidatorFactoryProvider;
import shared.utils.ValidatorUtil;

import java.util.List;

@AllArgsConstructor
public class UserController implements IUserController {
    IUserService userService;

    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        return userService.registerCustomer(customerDTO);
    }

    @Override
    public AirlineDTO registerAirline(AirlineDTO airlineDTO) {
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
    public List<String> getAllAirlinesNicknames() {
        return userService.getAllAirlines().stream()
                .map(AirlineDTO::getNickname)
                .toList();
    }

    @Override
    public UserDTO getUserByNickname(String nickname) {
        UserDTO userDTO = userService.getUserByNickname(nickname);
        return userDTO;
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO user) {
        return userService.updateUser(nickname, user);
    }

    @Override
    public AirlineDTO getAirlineByNickname(String nickname) {
        return userService.getAirlineDetailsByNickname(nickname);
    }
}
