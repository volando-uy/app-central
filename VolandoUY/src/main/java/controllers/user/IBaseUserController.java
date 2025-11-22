package controllers.user;

import domain.dtos.user.*;

import java.io.File;
import java.util.List;

public interface IBaseUserController {
    BaseAirlineDTO registerAirline(BaseAirlineDTO dto, File imageFile);


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
    boolean existsUserByNickname(String nickname);
    boolean existsUserByEmail(String email);

    // Simple and full versions
    UserDTO getUserSimpleDetailsByNickname(String nickname); // Devuelve BaseAirlineDTO o BaseCustomerDTO
    AirlineDTO getAirlineDetailsByNickname(String nickname);
    BaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname);
    CustomerDTO getCustomerDetailsByNickname(String nickname);

    // Follow section
    void followUser(String followerNickname, String followedNickname);
    void unfollowUser(String followerNickname, String followedNickname);
    Boolean isFollowing(String followerNickname, String followedNickname);
}
