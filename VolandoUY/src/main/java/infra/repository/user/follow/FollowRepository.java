package infra.repository.user.follow;


import app.DBConnection;
import domain.models.user.User;
import domain.models.user.follow.Follow;
import domain.models.user.follow.FollowKey;
import jakarta.persistence.EntityManager;

public class FollowRepository extends AbstractFollowRepository implements IFollowRepository {
    public FollowRepository() {
        super();
    }

    @Override
    public void delete(Follow follow) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            Follow managedFollow = em.merge(follow);
            em.remove(managedFollow);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void followUser(User follower, User followed) {
        FollowKey followKey = new FollowKey();
        followKey.setFollower(follower);
        followKey.setFollowed(followed);

        Follow follow = new Follow();
        follow.setId(followKey);

        this.save(follow);
    }

    @Override
    public void unfollowUser(User follower, User followed) {
        FollowKey followKey = new FollowKey();
        followKey.setFollower(follower);
        followKey.setFollowed(followed);

        Follow follow = this.findByKey(followKey);
        if (follow != null) {
            this.delete(follow);
        }
    }

    @Override
    public Boolean isFollowing(User follower, User followed) {
        FollowKey followKey = new FollowKey();
        followKey.setFollower(follower);
        followKey.setFollowed(followed);

        return this.existsByKey(followKey);
    }
}
