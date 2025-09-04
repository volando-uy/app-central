package controllers.user;

import domain.dtos.user.*;

import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUserController {
    BaseCustomerDTO registerCustomer(BaseCustomerDTO dto);
    BaseAirlineDTO registerAirline(BaseAirlineDTO dto);

    UserDTO updateUser(String nickname, UserDTO user);

    // Getters for all users
    // Simple and full versions
    List<UserDTO> getAllUsersDetails();
    List<UserDTO> getAllUsersSimpleDetails();

    List<String> getAllUsersNicknames();
    List<String> getAllAirlinesNicknames();

    // Getters for all users of a type
    // Simple and full versions
    List<CustomerDTO> getAllCustomersDetails();
    List<BaseCustomerDTO> getAllCustomersSimpleDetails();
    List<AirlineDTO> getAllAirlinesDetails();
    List<BaseAirlineDTO> getAllAirlinesSimpleDetails();

    // Getters by nickname
    // Simple and full versions
    UserDTO getUserSimpleDetailsByNickname(String nickname); // Devuelve BaseAirlineDTO o BaseCustomerDTO
    AirlineDTO getAirlineDetailsByNickname(String nickname);
    BaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname);
    CustomerDTO getCustomerDetailsByNickname(String nickname);
    BaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname);

    void addFlightRoutePackageToCustomer(String customerNickname, String packageName);
}
