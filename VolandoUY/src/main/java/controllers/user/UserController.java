package controllers.user;

import domain.dtos.user.*;
import domain.services.user.IUserService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserController implements IUserController {
    IUserService userService;

    @Override
    public BaseCustomerDTO registerCustomer(BaseCustomerDTO customerDTO) {
        return userService.registerCustomer(customerDTO);
    }

    @Override
    public BaseAirlineDTO registerAirline(BaseAirlineDTO airlineDTO) {
        return userService.registerAirline(airlineDTO);
    }

    @Override
    public List<UserDTO> getAllUsersDetails() {
        return userService.getAllUsers(true);
    }

    @Override
    public List<UserDTO> getAllUsersSimpleDetails() {
        return userService.getAllUsers(false);
    }

    @Override
    public List<String> getAllUsersNicknames() {
        return userService.getAllUsersNicknames();
    }

    @Override
    public List<CustomerDTO> getAllCustomersDetails() {
        return userService.getAllCustomersDetails(true);
    }

    @Override
    public List<BaseCustomerDTO> getAllCustomersSimpleDetails() {
        return userService.getAllCustomersDetails(false)
                .stream()
                .map(c -> (BaseCustomerDTO) c)
                .toList();
    }

    @Override
    public List<AirlineDTO> getAllAirlinesDetails() {
        return userService.getAllAirlinesDetails(true);
    }

    @Override
    public List<BaseAirlineDTO> getAllAirlinesSimpleDetails() {
        return userService.getAllAirlinesDetails(false)
                .stream()
                .map(a -> (BaseAirlineDTO) a)
                .toList();
    }

    @Override
    public List<String> getAllAirlinesNicknames() {
        return userService.getAllAirlinesDetails(false).stream()
                .map(BaseAirlineDTO::getNickname)
                .toList();
    }

    @Override
    public UserDTO getUserSimpleDetailsByNickname(String nickname) {
        return userService.getUserDetailsByNickname(nickname, false);
    }

    @Override
    public UserDTO updateUser(String nickname, UserDTO user) {
        return userService.updateUser(nickname, user);
    }

    @Override
    public AirlineDTO getAirlineDetailsByNickname(String nickname) {
        return userService.getAirlineDetailsByNickname(nickname, true);
    }

    @Override
    public BaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname) {
        return userService.getAirlineDetailsByNickname(nickname, false);
    }

    @Override
    public CustomerDTO getCustomerDetailsByNickname(String nickname) {
        return userService.getCustomerDetailsByNickname(nickname, true);
    }

    @Override
    public BaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname) {
        return userService.getCustomerDetailsByNickname(nickname, false);
    }

    @Override
    public void addFlightRoutePackageToCustomer(String customerNickname, String packageName) {
        userService.addFlightRoutePackageToCustomer(customerNickname, packageName);
    }


}
