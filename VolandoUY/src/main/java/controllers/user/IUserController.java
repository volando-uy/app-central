package controllers.user;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;

import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUserController {
    void registerCustomer(CustomerDTO dto);
    void registerAirline(AirlineDTO dto);
    List<UserDTO> getAllUsers();
    UserDTO getUserByNickname(String nickname);
    List<String> getAllUsersNicknames();
    UserDTO updateTemporalUser(UserDTO user);
    void updateUser(String nickname, UserDTO user);
}