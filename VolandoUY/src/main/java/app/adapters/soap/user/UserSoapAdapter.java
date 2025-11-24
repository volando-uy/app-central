package app.adapters.soap.user;

import app.adapters.dto.user.*;
import app.adapters.mappers.ImageMapper;
import app.adapters.mappers.UserSoapMapper;
import app.adapters.soap.BaseSoapAdapter;
import controllers.user.IUserController;
import controllers.user.IUserSoapController;
import domain.dtos.user.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class UserSoapAdapter extends BaseSoapAdapter implements IUserSoapController {

    private final IUserController controller;

    public UserSoapAdapter(IUserController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "userService";
    }

    @Override
    @WebMethod
    public SoapBaseCustomerDTO registerCustomer(SoapBaseCustomerDTO soapDto, String imageFileBase64) {
        BaseCustomerDTO domainDto = UserSoapMapper.toBaseCustomerDTO(soapDto);
        File imageFile = null;
        try {
            imageFile = ImageMapper.fromBase64(imageFileBase64, ".tmp");
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen base64", e);
        }
        BaseCustomerDTO registered = controller.registerCustomer(domainDto, imageFile);
        return UserSoapMapper.toSoapBaseCustomerDTO(registered);
    }

    @Override
    @WebMethod
    public SoapBaseAirlineDTO registerAirline(SoapBaseAirlineDTO dto, String imageFileBase64) {
        BaseAirlineDTO domainDto = UserSoapMapper.toBaseAirlineDTO(dto);
        File imageFile = null;
        try {
            imageFile = ImageMapper.fromBase64(imageFileBase64, ".tmp");
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen base64", e);
        }
        BaseAirlineDTO baseAirlineDTO = controller.registerAirline(domainDto, imageFile);
        return UserSoapMapper.toSoapBaseAirlineDTO(baseAirlineDTO);
    }

    @Override
    @WebMethod
    public SoapUserDTO updateUser(String nickname, SoapUserDTO updatedUserDTO, String imageBase64) {
        File imageFile = null;
        try {
            System.out.println("Usuario sin mappear para updateUser: " + updatedUserDTO.toString());
            UserDTO user = UserSoapMapper.toUserDTO(updatedUserDTO);
            System.out.println("Usuario mappeado para updateUser: " + user.toString());
            imageFile = ImageMapper.fromBase64(imageBase64, ".tmp");
            UserDTO updated = controller.updateUser(nickname, user, imageFile);
            System.out.println("Usuario actualizado: " + updated.toString());
            System.out.println("Usuairo actualizado mappeado a SOAP: " + UserSoapMapper.toSoapUserDTO(updated).toString());

            return UserSoapMapper.toSoapUserDTO(updated);
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen base64", e);
        }  catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
        }
        finally {
            if (imageFile != null && imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

//    @Override
//    @WebMethod
//    public UserDTO updateUserC(String nickname, SoapBaseCustomerDTO updatedUserDTO, String imageBase64) {
//        File imageFile = null;
//        try {
//            imageFile = ImageMapper.fromBase64(imageBase64, ".tmp");
//            BaseCustomerDTO domainDto = CustomerSoapMapper.toBaseCustomerDTO(updatedUserDTO);
//            System.out.println("Imagen procesada para updateUserC: " + (imageFile != null ? imageFile.getAbsolutePath() : "null"));
//            return controller.updateUser(nickname, domainDto, imageFile);
//        } catch (IOException e) {
//            throw new RuntimeException("Error al procesar la imagen base64", e);
//        } finally {
//            if (imageFile != null && imageFile.exists()) {
//                imageFile.delete();
//            }
//        }
//    }


    @Override
    @WebMethod
    public List<SoapUserDTO> getAllUsersDetails() {
        List<UserDTO> userDTOS = controller.getAllUsersDetails();
        return userDTOS.stream()
                .map(UserSoapMapper::toSoapUserDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapUserDTO> getAllUsersSimpleDetails() {

        List<UserDTO> users = controller.getAllUsersSimpleDetails();
        return users.stream()
                .map(UserSoapMapper::toSoapUserDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<String> getAllUsersNicknames() {
        return controller.getAllUsersNicknames();
    }

    @Override
    @WebMethod
    public List<String> getAllAirlinesNicknames() {
        return controller.getAllAirlinesNicknames();
    }

    @Override
    @WebMethod
    public List<SoapCustomerDTO> getAllCustomersDetails() {
        List<CustomerDTO> customerDTOS = controller.getAllCustomersDetails();
        return customerDTOS.stream()
                .map(UserSoapMapper::toSoapCustomerDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseCustomerDTO> getAllCustomersSimpleDetails() {
        List<BaseCustomerDTO> customerDTOS = controller.getAllCustomersSimpleDetails();
        return customerDTOS.stream()
                .map(UserSoapMapper::toSoapBaseCustomerDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapAirlineDTO> getAllAirlinesDetails() {
        List<AirlineDTO> airlines= controller.getAllAirlinesDetails();
        return airlines.stream()
                .map(UserSoapMapper::toSoapAirlineDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseAirlineDTO> getAllAirlinesSimpleDetails() {
        List<BaseAirlineDTO> airlineDTOS = controller.getAllAirlinesSimpleDetails();
        return airlineDTOS.stream()
                .map(UserSoapMapper::toSoapBaseAirlineDTO)
                .toList();
    }

    @Override
    @WebMethod
    public SoapUserDTO getUserSimpleDetailsByNickname(String nickname) {
        UserDTO userDTO = controller.getUserSimpleDetailsByNickname(nickname);
        return UserSoapMapper.toSoapUserDTO(userDTO);
    }

    @Override
    @WebMethod
    public SoapAirlineDTO getAirlineDetailsByNickname(String nickname) {
        AirlineDTO airlineDTO = controller.getAirlineDetailsByNickname(nickname);
        return UserSoapMapper.toSoapAirlineDTO(airlineDTO);
    }

    @Override
    @WebMethod
    public SoapBaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname) {
        BaseAirlineDTO baseAirlineDTO = controller.getAirlineSimpleDetailsByNickname(nickname);
        return UserSoapMapper.toSoapBaseAirlineDTO(baseAirlineDTO);
    }

    @Override
    @WebMethod
    public SoapCustomerDTO getCustomerDetailsByNickname(String nickname) {
        CustomerDTO customer = controller.getCustomerDetailsByNickname(nickname);
        return UserSoapMapper.toSoapCustomerDTO(customer);
    }

    @Override
    @WebMethod
    public SoapBaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname) {
        BaseCustomerDTO domainDto = controller.getCustomerSimpleDetailsByNickname(nickname);
        return UserSoapMapper.toSoapBaseCustomerDTO(domainDto);
    }

    @Override
    @WebMethod
    public void followUser(String followerNickname, String followedNickname) {
        controller.followUser(followerNickname, followedNickname);
    }

    @Override
    @WebMethod
    public void unfollowUser(String followerNickname, String followedNickname) {
        controller.unfollowUser(followerNickname, followedNickname);
    }

    @Override
    @WebMethod
    public Boolean isFollowing(String followerNickname, String followedNickname) {
        return controller.isFollowing(followerNickname, followedNickname);
    }

    @Override
    @WebMethod
    public boolean existsUserByNickname(String nickname) {
        return controller.existsUserByNickname(nickname);
    }

    @Override
    @WebMethod
    public boolean existsUserByEmail(String email) {
        return controller.existsUserByEmail(email);
    }
}
