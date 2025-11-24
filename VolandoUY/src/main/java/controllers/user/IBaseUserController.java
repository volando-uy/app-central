package controllers.user;

import domain.dtos.user.*;

import java.io.File;
import java.util.List;

public interface IBaseUserController {




    List<String> getAllUsersNicknames();
    List<String> getAllAirlinesNicknames();



    boolean existsUserByNickname(String nickname);
    boolean existsUserByEmail(String email);


    void followUser(String followerNickname, String followedNickname);
    void unfollowUser(String followerNickname, String followedNickname);
    Boolean isFollowing(String followerNickname, String followedNickname);

}
