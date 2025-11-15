package infra.repository.user.follow;

import domain.models.user.follow.Follow;
import infra.repository.BaseRepository;

public class AbstractFollowRepository extends BaseRepository<Follow> {
    public AbstractFollowRepository() {
        super(Follow.class);
    }
}
