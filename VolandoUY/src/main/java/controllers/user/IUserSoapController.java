package controllers.user;

import app.adapters.dto.user.*;

import java.util.List;

public interface IUserSoapController extends IBaseUserController{
    SoapUserDTO updateUser(String nickname, SoapUserDTO updatedUserDTO, String imageBase64);
    SoapBaseCustomerDTO registerCustomer(SoapBaseCustomerDTO soapDto, String imageFileBase64);
    SoapBaseAirlineDTO registerAirline(SoapBaseAirlineDTO dto, String imageFileBase64);

    SoapBaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname);
    SoapCustomerDTO getCustomerDetailsByNickname(String nickname);
    SoapUserDTO getUserSimpleDetailsByNickname(String nickname); // Devuelve BaseAirlineDTO o BaseCustomerDTO
    List<SoapCustomerDTO> getAllCustomersDetails();
    List<SoapBaseCustomerDTO> getAllCustomersSimpleDetails();
    List<SoapUserDTO> getAllUsersSimpleDetails();
    List<SoapUserDTO> getAllUsersDetails();

    List<SoapAirlineDTO> getAllAirlinesDetails();
    List<SoapBaseAirlineDTO> getAllAirlinesSimpleDetails();

    SoapAirlineDTO getAirlineDetailsByNickname(String nickname);
    SoapBaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname);
}
