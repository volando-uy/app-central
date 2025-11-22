package controllers.user;

import app.adapters.dto.customer.SoapBaseCustomerDTO;
import domain.dtos.user.UserDTO;

import java.io.File;

public interface IUserSoapController extends IBaseUserController{
    UserDTO updateUser(String nickname, UserDTO updatedUserDTO, String imageBase64);
    UserDTO updateUserC(String nickname, SoapBaseCustomerDTO updatedUserDTO, String imageBase64);
    SoapBaseCustomerDTO registerCustomer(SoapBaseCustomerDTO soapDto, File imageFile);
    SoapBaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname);

}
