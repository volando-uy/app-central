package infra.repository.user.follow;

import domain.models.ticket.Ticket;
import domain.models.user.User;
import domain.models.user.follow.Follow;
import infra.repository.IBaseRepository;


public interface IFollowRepository extends IBaseRepository<Follow> {
    void delete(Follow follow);
    void followUser(User follower, User followed);
    void unfollowUser(User follower, User followed);
    Boolean isFollowing(User follower, User followed);
}
