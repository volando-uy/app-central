package app.adapters.soap.user;

import app.adapters.soap.BaseSoapAdapter;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.io.File;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class UserSoapAdapter extends BaseSoapAdapter implements IUserController {

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
    public BaseCustomerDTO registerCustomer(BaseCustomerDTO dto, File imageFile) {
        return controller.registerCustomer(dto, imageFile);
    }

    @Override
    @WebMethod
    public BaseAirlineDTO registerAirline(BaseAirlineDTO dto, File imageFile) {
        return controller.registerAirline(dto, imageFile);
    }

    @Override
    @WebMethod
    public UserDTO updateUser(String nickname, UserDTO user, File imageFile) {
        return controller.updateUser(nickname, user, imageFile);
    }

    @Override
    @WebMethod
    public List<UserDTO> getAllUsersDetails() {
        return controller.getAllUsersDetails();
    }

    @Override
    @WebMethod
    public List<UserDTO> getAllUsersSimpleDetails() {
        return controller.getAllUsersSimpleDetails();
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
    public List<CustomerDTO> getAllCustomersDetails() {
        return controller.getAllCustomersDetails();
    }

    @Override
    @WebMethod
    public List<BaseCustomerDTO> getAllCustomersSimpleDetails() {
        return controller.getAllCustomersSimpleDetails();
    }

    @Override
    @WebMethod
    public List<AirlineDTO> getAllAirlinesDetails() {
        return controller.getAllAirlinesDetails();
    }

    @Override
    @WebMethod
    public List<BaseAirlineDTO> getAllAirlinesSimpleDetails() {
        return controller.getAllAirlinesSimpleDetails();
    }

    @Override
    @WebMethod
    public UserDTO getUserSimpleDetailsByNickname(String nickname) {
        return controller.getUserSimpleDetailsByNickname(nickname);
    }

    @Override
    @WebMethod
    public AirlineDTO getAirlineDetailsByNickname(String nickname) {
        return controller.getAirlineDetailsByNickname(nickname);
    }

    @Override
    @WebMethod
    public BaseAirlineDTO getAirlineSimpleDetailsByNickname(String nickname) {
        return controller.getAirlineSimpleDetailsByNickname(nickname);
    }

    @Override
    @WebMethod
    public CustomerDTO getCustomerDetailsByNickname(String nickname) {
        return controller.getCustomerDetailsByNickname(nickname);
    }

    @Override
    @WebMethod
    public BaseCustomerDTO getCustomerSimpleDetailsByNickname(String nickname) {
        return controller.getCustomerSimpleDetailsByNickname(nickname);
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
}
