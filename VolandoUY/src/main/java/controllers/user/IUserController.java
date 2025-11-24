package controllers.user;

import app.adapters.dto.user.SoapUserDTO;
import domain.dtos.user.*;

import java.io.File;
import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUserController extends IBaseUserController {
    UserDTO updateUser(String nickname, UserDTO user, File imageFile);
    BaseCustomerDTO registerCustomer(BaseCustomerDTO dto, File imageFile);
    BaseAirlineDTO registerAirline(BaseAirlineDTO dto, File imageFile);
    BaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname);
    CustomerDTO getCustomerDetailsByNickname(String nickname);
    UserDTO getUserSimpleDetailsByNickname(String nickname); // Devuelve BaseAirlineDTO o BaseCustomerDTO
    List<CustomerDTO> getAllCustomersDetails();
    List<BaseCustomerDTO> getAllCustomersSimpleDetails();
    List<UserDTO> getAllUsersSimpleDetails();
    List<UserDTO> getAllUsersDetails();

    List<AirlineDTO> getAllAirlinesDetails();
    List<BaseAirlineDTO> getAllAirlinesSimpleDetails();

    AirlineDTO getAirlineDetailsByNickname(String nickname);
    BaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname);
}
